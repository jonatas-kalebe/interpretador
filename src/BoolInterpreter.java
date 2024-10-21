import java.util.List;

public class BoolInterpreter {
    public static void main(String[] args) {
        String instruction= """
            main()
            vars p, b, x
            begin
            new Base
            store b
            new Pessoa
            store p
            load b
            load p
            set _prototype
            const 111
            load b
            set id
            const 123
            load p
            set num
            const 321
            load p
            set id
            const 1024
            store x
            load p
            call showid
            pop
            load x
            load p
            call calc
            pop
            end""";
        Main mainMethod=new Main(instruction);
        mainMethod.process();


    }

}
