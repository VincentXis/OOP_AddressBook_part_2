package listFileHandler;

import contactRegister.Register;

public class AutoSave implements Runnable {
    private Register reg;
    private boolean run = true;

    public AutoSave(Register reg) {
        this.reg = reg;
    }

    @Override
    public void run() {
        new Thread().start();
        while (run) {
            try {

                Thread.sleep(10000);
                reg.saveContactList();
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
        }
    }

    public void stopThread(boolean run) {
        this.run = run;
    }
}
