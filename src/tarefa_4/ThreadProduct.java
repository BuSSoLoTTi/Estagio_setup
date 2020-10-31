package tarefa_4;

import java.lang.ref.Cleaner;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.PrimitiveIterator;

public class ThreadProduct  extends Thread {
    private static final int MAX_INSTACE=3;
    private static int instance=0;

    public ThreadProduct(){
        instance++;
    }

    public ThreadProduct(String nome){
        instance++;
        super.setName(nome);
    }

    @Override
    public void run() {

    }
     public static boolean disponivel(){
        return instance<MAX_INSTACE;
     }


    @Override
    protected void finalize() throws Throwable {
        instance--;
    }
}
