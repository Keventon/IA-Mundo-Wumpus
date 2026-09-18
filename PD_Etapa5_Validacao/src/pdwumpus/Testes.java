package pdwumpus;
public final class Testes {
  public static void main(String[] args) {
    TestesAmbiente.testar();
    TestesMotor.testar();
    TestesMemoria.testar();
    TestesAG.testar();
    System.out.println("TODOS OS TESTES PASSARAM.");
  }
}
