package com.google.android.gms.internal;

public abstract class em {
    private final Runnable lg = new Runnable(this) {
        final /* synthetic */ em sg;

        {
            this.sg = r1;
        }

        public final void run() {
            this.sg.sf = Thread.currentThread();
            this.sg.bh();
        }
    };
    private volatile Thread sf;

    public abstract void bh();

    public final void cancel() {
        onStop();
        if (this.sf != null) {
            this.sf.interrupt();
        }
    }

    public abstract void onStop();

    public final void start() {
        en.execute(this.lg);
    }
}
