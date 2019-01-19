package ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestForkJoin {

    class FiboTask extends RecursiveTask<Integer>{

        private final Integer x;

        public FiboTask(Integer x){
            this.x = x;
        }

        @Override
        protected Integer compute() {
            if(x<2) return x;
            FiboTask fiboTask=new FiboTask(x-2);
            fiboTask.fork();
            return new FiboTask(x-1).compute()+fiboTask.join();
        }
    }

    class FiboAction extends RecursiveAction{

        private final Integer x;
        private Integer sum=0;

        public FiboAction(Integer x){
            this.x = x;
        }

        @Override
        protected void compute() {
            if(x<2) sum+=x;
            else {
                FiboAction fibo1=new FiboAction(x-1);
                FiboAction fibo2=new FiboAction(x-2);
                invokeAll(fibo1,fibo2);
                sum=fibo1.sum+fibo2.sum;
            }
        }
    }


    @Test
    public void testForkJoinTask(){
        ForkJoinTask<Integer> forkJoinTask=new FiboTask(7);
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        assertEquals(Integer.valueOf(13),forkJoinPool.invoke(forkJoinTask));
    }

    @Test
    public void testForkJoinAction(){
        ForkJoinTask<?> forkJoinTask=new FiboAction(7);
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        assertNull(forkJoinPool.invoke(forkJoinTask));
        assertEquals(Integer.valueOf(13),((FiboAction)forkJoinTask).sum);
    }
}
