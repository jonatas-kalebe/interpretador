package compilador.entidades;

import compilador.estruturas.Arguments;
import compilador.estruturas.BodyStatements;
import compilador.estruturas.IfStatements;
import compilador.estruturas.MainStatements;

public class Attribution implements IfStatements, BodyStatements, MainStatements, Arguments {
    private final Name variavel;
    private final boolean valorDefinido;
    private Arguments valor;
    private Name operando1;
    private Name operando2;
    private String operador;

    public Attribution(Name variavel, Arguments valor) {
        this.variavel = variavel;
        this.valor = valor;
        valorDefinido = true;

    }

    public Attribution(Name variavel, Name operando1, Name operando2, String operador) {
        this.variavel = variavel;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operador = operador;
        valorDefinido = false;
    }

    private String processLogic() {
        if (valorDefinido) {
            return valor.compileCode() + variavel.compileCode();
        } else {
            return operando1.compileCode() + operando2.compileCode() + Operation.getOperation(operador) + variavel.compileCode();
        }
    }


    @Override
    public String compileCode() {
        return processLogic();
    }
}