package pdwumpus;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;
import javax.imageio.ImageIO;

/** PNGs independentes de bibliotecas externas, inclusive em execução sem janela. */
public final class Graficos {
    private Graficos() { }
    public static void linhas(Path arquivo,String titulo,String eixoX,String eixoY,
            double[] xs,double[][] ys,String[] nomes) throws IOException {
        int w=1200,h=700,l=105,r=55,t=80,b=125;
        BufferedImage imagem=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics2D g=imagem.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);g.fillRect(0,0,w,h);
        double min=Double.POSITIVE_INFINITY,max=Double.NEGATIVE_INFINITY;
        for(double[] serie:ys) for(double v:serie) {min=Math.min(min,v);max=Math.max(max,v);}
        if(min==max) {min-=1;max+=1;}
        double folga=(max-min)*0.1;min-=folga;max+=folga;
        if(eixoY.equals("Vitórias (%)")) {min=0;max=100;}
        double xmin=xs[0],xmax=xs[xs.length-1];if(xmin==xmax) xmax++;
        g.setFont(new Font("SansSerif",Font.BOLD,24));g.setColor(Color.BLACK);g.drawString(titulo,l,40);
        g.setFont(new Font("SansSerif",Font.PLAIN,17));
        for(int k=0;k<=5;k++) {
            int py=t+(h-t-b)*k/5;
            g.setColor(new Color(225,225,225));g.drawLine(l,py,w-r,py);
            g.setColor(Color.DARK_GRAY);g.drawString(String.format(java.util.Locale.US,"%.1f",max-(max-min)*k/5),10,py+6);
        }
        int passo=Math.max(1,(xs.length-1)/5);
        for(int k=0;k<xs.length;k+=passo) {
            int px=l+(int)((xs[k]-xmin)/(xmax-xmin)*(w-l-r));
            g.setColor(Color.DARK_GRAY);g.drawString(String.format(java.util.Locale.US,"%.0f",xs[k]),px-10,h-b+28);
        }
        g.drawLine(l,t,l,h-b);g.drawLine(l,h-b,w-r,h-b);
        g.drawString(eixoX,w/2-40,h-b+60);g.drawString(eixoY,l,t-15);
        Color[] cores={new Color(30,85,170),new Color(180,55,45),new Color(35,130,80)};
        for(int s=0;s<ys.length;s++) {
            g.setColor(cores[s%cores.length]);g.setStroke(new BasicStroke(2.5f));
            for(int k=1;k<xs.length;k++) {
                int x1=l+(int)((xs[k-1]-xmin)/(xmax-xmin)*(w-l-r));
                int x2=l+(int)((xs[k]-xmin)/(xmax-xmin)*(w-l-r));
                int y1=t+(int)((max-ys[s][k-1])/(max-min)*(h-t-b));
                int y2=t+(int)((max-ys[s][k])/(max-min)*(h-t-b));g.drawLine(x1,y1,x2,y2);
            }
            if(xs.length<20) for(int k=0;k<xs.length;k++) {
                int px=l+(int)((xs[k]-xmin)/(xmax-xmin)*(w-l-r));
                int py=t+(int)((max-ys[s][k])/(max-min)*(h-t-b));g.fillOval(px-4,py-4,8,8);
            }
            int lx=l+s*320;g.drawLine(lx,h-28,lx+30,h-28);g.drawString(nomes[s],lx+40,h-22);
        }
        g.dispose();Files.createDirectories(arquivo.toAbsolutePath().getParent());ImageIO.write(imagem,"png",arquivo.toFile());
    }
}
