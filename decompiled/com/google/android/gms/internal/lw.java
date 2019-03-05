package com.google.android.gms.internal;

import java.io.IOException;

public final class lw extends ma<lw> {
    public a[] amr;

    public static final class a extends ma<a> {
        private static volatile a[] ams;
        public a amt;
        public String name;

        public static final class a extends ma<a> {
            private static volatile a[] amu;
            public a amv;
            public int type;

            public static final class a extends ma<a> {
                public long amA;
                public int amB;
                public int amC;
                public boolean amD;
                public a[] amE;
                public a[] amF;
                public String[] amG;
                public long[] amH;
                public float[] amI;
                public long amJ;
                public byte[] amw;
                public String amx;
                public double amy;
                public float amz;

                public a() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r0 = this;
                    r0.<init>();
                    r0.nA();
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.a.<init>():void");
                }

                public void a(com.google.android.gms.internal.lz r9) throws java.io.IOException {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r8 = this;
                    r6 = 0;
                    r1 = 0;
                    r0 = r8.amw;
                    r2 = com.google.android.gms.internal.mh.ank;
                    r0 = java.util.Arrays.equals(r0, r2);
                    if (r0 != 0) goto L_0x0013;
                L_0x000d:
                    r0 = 1;
                    r2 = r8.amw;
                    r9.a(r0, r2);
                L_0x0013:
                    r0 = r8.amx;
                    r2 = "";
                    r0 = r0.equals(r2);
                    if (r0 != 0) goto L_0x0023;
                L_0x001d:
                    r0 = 2;
                    r2 = r8.amx;
                    r9.b(r0, r2);
                L_0x0023:
                    r2 = r8.amy;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r4 = 0;
                    r4 = java.lang.Double.doubleToLongBits(r4);
                    r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r0 == 0) goto L_0x0039;
                L_0x0033:
                    r0 = 3;
                    r2 = r8.amy;
                    r9.a(r0, r2);
                L_0x0039:
                    r0 = r8.amz;
                    r0 = java.lang.Float.floatToIntBits(r0);
                    r2 = 0;
                    r2 = java.lang.Float.floatToIntBits(r2);
                    if (r0 == r2) goto L_0x004c;
                L_0x0046:
                    r0 = 4;
                    r2 = r8.amz;
                    r9.b(r0, r2);
                L_0x004c:
                    r2 = r8.amA;
                    r0 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
                    if (r0 == 0) goto L_0x0058;
                L_0x0052:
                    r0 = 5;
                    r2 = r8.amA;
                    r9.b(r0, r2);
                L_0x0058:
                    r0 = r8.amB;
                    if (r0 == 0) goto L_0x0062;
                L_0x005c:
                    r0 = 6;
                    r2 = r8.amB;
                    r9.p(r0, r2);
                L_0x0062:
                    r0 = r8.amC;
                    if (r0 == 0) goto L_0x006c;
                L_0x0066:
                    r0 = 7;
                    r2 = r8.amC;
                    r9.q(r0, r2);
                L_0x006c:
                    r0 = r8.amD;
                    if (r0 == 0) goto L_0x0077;
                L_0x0070:
                    r0 = 8;
                    r2 = r8.amD;
                    r9.a(r0, r2);
                L_0x0077:
                    r0 = r8.amE;
                    if (r0 == 0) goto L_0x0094;
                L_0x007b:
                    r0 = r8.amE;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x0094;
                L_0x0080:
                    r0 = r1;
                L_0x0081:
                    r2 = r8.amE;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x0094;
                L_0x0086:
                    r2 = r8.amE;
                    r2 = r2[r0];
                    if (r2 == 0) goto L_0x0091;
                L_0x008c:
                    r3 = 9;
                    r9.a(r3, r2);
                L_0x0091:
                    r0 = r0 + 1;
                    goto L_0x0081;
                L_0x0094:
                    r0 = r8.amF;
                    if (r0 == 0) goto L_0x00b1;
                L_0x0098:
                    r0 = r8.amF;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x00b1;
                L_0x009d:
                    r0 = r1;
                L_0x009e:
                    r2 = r8.amF;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x00b1;
                L_0x00a3:
                    r2 = r8.amF;
                    r2 = r2[r0];
                    if (r2 == 0) goto L_0x00ae;
                L_0x00a9:
                    r3 = 10;
                    r9.a(r3, r2);
                L_0x00ae:
                    r0 = r0 + 1;
                    goto L_0x009e;
                L_0x00b1:
                    r0 = r8.amG;
                    if (r0 == 0) goto L_0x00ce;
                L_0x00b5:
                    r0 = r8.amG;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x00ce;
                L_0x00ba:
                    r0 = r1;
                L_0x00bb:
                    r2 = r8.amG;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x00ce;
                L_0x00c0:
                    r2 = r8.amG;
                    r2 = r2[r0];
                    if (r2 == 0) goto L_0x00cb;
                L_0x00c6:
                    r3 = 11;
                    r9.b(r3, r2);
                L_0x00cb:
                    r0 = r0 + 1;
                    goto L_0x00bb;
                L_0x00ce:
                    r0 = r8.amH;
                    if (r0 == 0) goto L_0x00e9;
                L_0x00d2:
                    r0 = r8.amH;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x00e9;
                L_0x00d7:
                    r0 = r1;
                L_0x00d8:
                    r2 = r8.amH;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x00e9;
                L_0x00dd:
                    r2 = 12;
                    r3 = r8.amH;
                    r3 = r3[r0];
                    r9.b(r2, r3);
                    r0 = r0 + 1;
                    goto L_0x00d8;
                L_0x00e9:
                    r2 = r8.amJ;
                    r0 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
                    if (r0 == 0) goto L_0x00f6;
                L_0x00ef:
                    r0 = 13;
                    r2 = r8.amJ;
                    r9.b(r0, r2);
                L_0x00f6:
                    r0 = r8.amI;
                    if (r0 == 0) goto L_0x0110;
                L_0x00fa:
                    r0 = r8.amI;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x0110;
                L_0x00ff:
                    r0 = r8.amI;
                    r0 = r0.length;
                    if (r1 >= r0) goto L_0x0110;
                L_0x0104:
                    r0 = 14;
                    r2 = r8.amI;
                    r2 = r2[r1];
                    r9.b(r0, r2);
                    r1 = r1 + 1;
                    goto L_0x00ff;
                L_0x0110:
                    super.a(r9);
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.a.a(com.google.android.gms.internal.lz):void");
                }

                public /* synthetic */ com.google.android.gms.internal.me b(com.google.android.gms.internal.ly r2) throws java.io.IOException {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r1 = this;
                    r0 = r1.t(r2);
                    return r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.a.b(com.google.android.gms.internal.ly):com.google.android.gms.internal.me");
                }

                protected int c() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r8 = this;
                    r6 = 0;
                    r1 = 0;
                    r0 = super.c();
                    r2 = r8.amw;
                    r3 = com.google.android.gms.internal.mh.ank;
                    r2 = java.util.Arrays.equals(r2, r3);
                    if (r2 != 0) goto L_0x0019;
                L_0x0011:
                    r2 = 1;
                    r3 = r8.amw;
                    r2 = com.google.android.gms.internal.lz.b(r2, r3);
                    r0 = r0 + r2;
                L_0x0019:
                    r2 = r8.amx;
                    r3 = "";
                    r2 = r2.equals(r3);
                    if (r2 != 0) goto L_0x002b;
                L_0x0023:
                    r2 = 2;
                    r3 = r8.amx;
                    r2 = com.google.android.gms.internal.lz.h(r2, r3);
                    r0 = r0 + r2;
                L_0x002b:
                    r2 = r8.amy;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r4 = 0;
                    r4 = java.lang.Double.doubleToLongBits(r4);
                    r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r2 == 0) goto L_0x0043;
                L_0x003b:
                    r2 = 3;
                    r3 = r8.amy;
                    r2 = com.google.android.gms.internal.lz.b(r2, r3);
                    r0 = r0 + r2;
                L_0x0043:
                    r2 = r8.amz;
                    r2 = java.lang.Float.floatToIntBits(r2);
                    r3 = 0;
                    r3 = java.lang.Float.floatToIntBits(r3);
                    if (r2 == r3) goto L_0x0058;
                L_0x0050:
                    r2 = 4;
                    r3 = r8.amz;
                    r2 = com.google.android.gms.internal.lz.c(r2, r3);
                    r0 = r0 + r2;
                L_0x0058:
                    r2 = r8.amA;
                    r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
                    if (r2 == 0) goto L_0x0066;
                L_0x005e:
                    r2 = 5;
                    r3 = r8.amA;
                    r2 = com.google.android.gms.internal.lz.d(r2, r3);
                    r0 = r0 + r2;
                L_0x0066:
                    r2 = r8.amB;
                    if (r2 == 0) goto L_0x0072;
                L_0x006a:
                    r2 = 6;
                    r3 = r8.amB;
                    r2 = com.google.android.gms.internal.lz.r(r2, r3);
                    r0 = r0 + r2;
                L_0x0072:
                    r2 = r8.amC;
                    if (r2 == 0) goto L_0x007e;
                L_0x0076:
                    r2 = 7;
                    r3 = r8.amC;
                    r2 = com.google.android.gms.internal.lz.s(r2, r3);
                    r0 = r0 + r2;
                L_0x007e:
                    r2 = r8.amD;
                    if (r2 == 0) goto L_0x008b;
                L_0x0082:
                    r2 = 8;
                    r3 = r8.amD;
                    r2 = com.google.android.gms.internal.lz.b(r2, r3);
                    r0 = r0 + r2;
                L_0x008b:
                    r2 = r8.amE;
                    if (r2 == 0) goto L_0x00ac;
                L_0x008f:
                    r2 = r8.amE;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x00ac;
                L_0x0094:
                    r2 = r0;
                    r0 = r1;
                L_0x0096:
                    r3 = r8.amE;
                    r3 = r3.length;
                    if (r0 >= r3) goto L_0x00ab;
                L_0x009b:
                    r3 = r8.amE;
                    r3 = r3[r0];
                    if (r3 == 0) goto L_0x00a8;
                L_0x00a1:
                    r4 = 9;
                    r3 = com.google.android.gms.internal.lz.b(r4, r3);
                    r2 = r2 + r3;
                L_0x00a8:
                    r0 = r0 + 1;
                    goto L_0x0096;
                L_0x00ab:
                    r0 = r2;
                L_0x00ac:
                    r2 = r8.amF;
                    if (r2 == 0) goto L_0x00cd;
                L_0x00b0:
                    r2 = r8.amF;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x00cd;
                L_0x00b5:
                    r2 = r0;
                    r0 = r1;
                L_0x00b7:
                    r3 = r8.amF;
                    r3 = r3.length;
                    if (r0 >= r3) goto L_0x00cc;
                L_0x00bc:
                    r3 = r8.amF;
                    r3 = r3[r0];
                    if (r3 == 0) goto L_0x00c9;
                L_0x00c2:
                    r4 = 10;
                    r3 = com.google.android.gms.internal.lz.b(r4, r3);
                    r2 = r2 + r3;
                L_0x00c9:
                    r0 = r0 + 1;
                    goto L_0x00b7;
                L_0x00cc:
                    r0 = r2;
                L_0x00cd:
                    r2 = r8.amG;
                    if (r2 == 0) goto L_0x00f2;
                L_0x00d1:
                    r2 = r8.amG;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x00f2;
                L_0x00d6:
                    r2 = r1;
                    r3 = r1;
                    r4 = r1;
                L_0x00d9:
                    r5 = r8.amG;
                    r5 = r5.length;
                    if (r2 >= r5) goto L_0x00ee;
                L_0x00de:
                    r5 = r8.amG;
                    r5 = r5[r2];
                    if (r5 == 0) goto L_0x00eb;
                L_0x00e4:
                    r4 = r4 + 1;
                    r5 = com.google.android.gms.internal.lz.cz(r5);
                    r3 = r3 + r5;
                L_0x00eb:
                    r2 = r2 + 1;
                    goto L_0x00d9;
                L_0x00ee:
                    r0 = r0 + r3;
                    r2 = r4 * 1;
                    r0 = r0 + r2;
                L_0x00f2:
                    r2 = r8.amH;
                    if (r2 == 0) goto L_0x0114;
                L_0x00f6:
                    r2 = r8.amH;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x0114;
                L_0x00fb:
                    r2 = r1;
                L_0x00fc:
                    r3 = r8.amH;
                    r3 = r3.length;
                    if (r1 >= r3) goto L_0x010d;
                L_0x0101:
                    r3 = r8.amH;
                    r3 = r3[r1];
                    r3 = com.google.android.gms.internal.lz.D(r3);
                    r2 = r2 + r3;
                    r1 = r1 + 1;
                    goto L_0x00fc;
                L_0x010d:
                    r0 = r0 + r2;
                    r1 = r8.amH;
                    r1 = r1.length;
                    r1 = r1 * 1;
                    r0 = r0 + r1;
                L_0x0114:
                    r1 = r8.amJ;
                    r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
                    if (r1 == 0) goto L_0x0123;
                L_0x011a:
                    r1 = 13;
                    r2 = r8.amJ;
                    r1 = com.google.android.gms.internal.lz.d(r1, r2);
                    r0 = r0 + r1;
                L_0x0123:
                    r1 = r8.amI;
                    if (r1 == 0) goto L_0x0138;
                L_0x0127:
                    r1 = r8.amI;
                    r1 = r1.length;
                    if (r1 <= 0) goto L_0x0138;
                L_0x012c:
                    r1 = r8.amI;
                    r1 = r1.length;
                    r1 = r1 * 4;
                    r0 = r0 + r1;
                    r1 = r8.amI;
                    r1 = r1.length;
                    r1 = r1 * 1;
                    r0 = r0 + r1;
                L_0x0138:
                    return r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.a.c():int");
                }

                public boolean equals(java.lang.Object r7) {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r6 = this;
                    r1 = 1;
                    r0 = 0;
                    if (r7 != r6) goto L_0x0006;
                L_0x0004:
                    r0 = r1;
                L_0x0005:
                    return r0;
                L_0x0006:
                    r2 = r7 instanceof com.google.android.gms.internal.lw.a.a.a;
                    if (r2 == 0) goto L_0x0005;
                L_0x000a:
                    r7 = (com.google.android.gms.internal.lw.a.a.a) r7;
                    r2 = r6.amw;
                    r3 = r7.amw;
                    r2 = java.util.Arrays.equals(r2, r3);
                    if (r2 == 0) goto L_0x0005;
                L_0x0016:
                    r2 = r6.amx;
                    if (r2 != 0) goto L_0x00ab;
                L_0x001a:
                    r2 = r7.amx;
                    if (r2 != 0) goto L_0x0005;
                L_0x001e:
                    r2 = r6.amy;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r4 = r7.amy;
                    r4 = java.lang.Double.doubleToLongBits(r4);
                    r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r2 != 0) goto L_0x0005;
                L_0x002e:
                    r2 = r6.amz;
                    r2 = java.lang.Float.floatToIntBits(r2);
                    r3 = r7.amz;
                    r3 = java.lang.Float.floatToIntBits(r3);
                    if (r2 != r3) goto L_0x0005;
                L_0x003c:
                    r2 = r6.amA;
                    r4 = r7.amA;
                    r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r2 != 0) goto L_0x0005;
                L_0x0044:
                    r2 = r6.amB;
                    r3 = r7.amB;
                    if (r2 != r3) goto L_0x0005;
                L_0x004a:
                    r2 = r6.amC;
                    r3 = r7.amC;
                    if (r2 != r3) goto L_0x0005;
                L_0x0050:
                    r2 = r6.amD;
                    r3 = r7.amD;
                    if (r2 != r3) goto L_0x0005;
                L_0x0056:
                    r2 = r6.amE;
                    r3 = r7.amE;
                    r2 = com.google.android.gms.internal.mc.equals(r2, r3);
                    if (r2 == 0) goto L_0x0005;
                L_0x0060:
                    r2 = r6.amF;
                    r3 = r7.amF;
                    r2 = com.google.android.gms.internal.mc.equals(r2, r3);
                    if (r2 == 0) goto L_0x0005;
                L_0x006a:
                    r2 = r6.amG;
                    r3 = r7.amG;
                    r2 = com.google.android.gms.internal.mc.equals(r2, r3);
                    if (r2 == 0) goto L_0x0005;
                L_0x0074:
                    r2 = r6.amH;
                    r3 = r7.amH;
                    r2 = com.google.android.gms.internal.mc.equals(r2, r3);
                    if (r2 == 0) goto L_0x0005;
                L_0x007e:
                    r2 = r6.amI;
                    r3 = r7.amI;
                    r2 = com.google.android.gms.internal.mc.equals(r2, r3);
                    if (r2 == 0) goto L_0x0005;
                L_0x0088:
                    r2 = r6.amJ;
                    r4 = r7.amJ;
                    r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r2 != 0) goto L_0x0005;
                L_0x0090:
                    r2 = r6.amX;
                    if (r2 == 0) goto L_0x009c;
                L_0x0094:
                    r2 = r6.amX;
                    r2 = r2.isEmpty();
                    if (r2 == 0) goto L_0x00b7;
                L_0x009c:
                    r2 = r7.amX;
                    if (r2 == 0) goto L_0x00a8;
                L_0x00a0:
                    r2 = r7.amX;
                    r2 = r2.isEmpty();
                    if (r2 == 0) goto L_0x0005;
                L_0x00a8:
                    r0 = r1;
                    goto L_0x0005;
                L_0x00ab:
                    r2 = r6.amx;
                    r3 = r7.amx;
                    r2 = r2.equals(r3);
                    if (r2 != 0) goto L_0x001e;
                L_0x00b5:
                    goto L_0x0005;
                L_0x00b7:
                    r0 = r6.amX;
                    r1 = r7.amX;
                    r0 = r0.equals(r1);
                    goto L_0x0005;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.a.equals(java.lang.Object):boolean");
                }

                public int hashCode() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r7 = this;
                    r1 = 0;
                    r6 = 32;
                    r0 = r7.amw;
                    r0 = java.util.Arrays.hashCode(r0);
                    r0 = r0 + 527;
                    r2 = r0 * 31;
                    r0 = r7.amx;
                    if (r0 != 0) goto L_0x008d;
                L_0x0011:
                    r0 = r1;
                L_0x0012:
                    r0 = r0 + r2;
                    r2 = r7.amy;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r0 = r0 * 31;
                    r4 = r2 >>> r6;
                    r2 = r2 ^ r4;
                    r2 = (int) r2;
                    r0 = r0 + r2;
                    r0 = r0 * 31;
                    r2 = r7.amz;
                    r2 = java.lang.Float.floatToIntBits(r2);
                    r0 = r0 + r2;
                    r0 = r0 * 31;
                    r2 = r7.amA;
                    r4 = r7.amA;
                    r4 = r4 >>> r6;
                    r2 = r2 ^ r4;
                    r2 = (int) r2;
                    r0 = r0 + r2;
                    r0 = r0 * 31;
                    r2 = r7.amB;
                    r0 = r0 + r2;
                    r0 = r0 * 31;
                    r2 = r7.amC;
                    r0 = r0 + r2;
                    r2 = r0 * 31;
                    r0 = r7.amD;
                    if (r0 == 0) goto L_0x0095;
                L_0x0043:
                    r0 = 1231; // 0x4cf float:1.725E-42 double:6.08E-321;
                L_0x0045:
                    r0 = r0 + r2;
                    r0 = r0 * 31;
                    r2 = r7.amE;
                    r2 = com.google.android.gms.internal.mc.hashCode(r2);
                    r0 = r0 + r2;
                    r0 = r0 * 31;
                    r2 = r7.amF;
                    r2 = com.google.android.gms.internal.mc.hashCode(r2);
                    r0 = r0 + r2;
                    r0 = r0 * 31;
                    r2 = r7.amG;
                    r2 = com.google.android.gms.internal.mc.hashCode(r2);
                    r0 = r0 + r2;
                    r0 = r0 * 31;
                    r2 = r7.amH;
                    r2 = com.google.android.gms.internal.mc.hashCode(r2);
                    r0 = r0 + r2;
                    r0 = r0 * 31;
                    r2 = r7.amI;
                    r2 = com.google.android.gms.internal.mc.hashCode(r2);
                    r0 = r0 + r2;
                    r0 = r0 * 31;
                    r2 = r7.amJ;
                    r4 = r7.amJ;
                    r4 = r4 >>> r6;
                    r2 = r2 ^ r4;
                    r2 = (int) r2;
                    r0 = r0 + r2;
                    r0 = r0 * 31;
                    r2 = r7.amX;
                    if (r2 == 0) goto L_0x008b;
                L_0x0083:
                    r2 = r7.amX;
                    r2 = r2.isEmpty();
                    if (r2 == 0) goto L_0x0098;
                L_0x008b:
                    r0 = r0 + r1;
                    return r0;
                L_0x008d:
                    r0 = r7.amx;
                    r0 = r0.hashCode();
                    goto L_0x0012;
                L_0x0095:
                    r0 = 1237; // 0x4d5 float:1.733E-42 double:6.11E-321;
                    goto L_0x0045;
                L_0x0098:
                    r1 = r7.amX;
                    r1 = r1.hashCode();
                    goto L_0x008b;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.a.hashCode():int");
                }

                public com.google.android.gms.internal.lw.a.a.a nA() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r5 = this;
                    r3 = 0;
                    r2 = 0;
                    r0 = com.google.android.gms.internal.mh.ank;
                    r5.amw = r0;
                    r0 = "";
                    r5.amx = r0;
                    r0 = 0;
                    r5.amy = r0;
                    r0 = 0;
                    r5.amz = r0;
                    r5.amA = r3;
                    r5.amB = r2;
                    r5.amC = r2;
                    r5.amD = r2;
                    r0 = com.google.android.gms.internal.lw.a.nw();
                    r5.amE = r0;
                    r0 = com.google.android.gms.internal.lw.a.a.ny();
                    r5.amF = r0;
                    r0 = com.google.android.gms.internal.mh.ani;
                    r5.amG = r0;
                    r0 = com.google.android.gms.internal.mh.ane;
                    r5.amH = r0;
                    r0 = com.google.android.gms.internal.mh.anf;
                    r5.amI = r0;
                    r5.amJ = r3;
                    r0 = 0;
                    r5.amX = r0;
                    r0 = -1;
                    r5.anb = r0;
                    return r5;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.a.nA():com.google.android.gms.internal.lw$a$a$a");
                }

                public com.google.android.gms.internal.lw.a.a.a t(com.google.android.gms.internal.ly r7) throws java.io.IOException {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r6 = this;
                    r1 = 0;
                L_0x0001:
                    r0 = r7.nB();
                    switch(r0) {
                        case 0: goto L_0x000e;
                        case 10: goto L_0x000f;
                        case 18: goto L_0x0016;
                        case 25: goto L_0x001d;
                        case 37: goto L_0x0024;
                        case 40: goto L_0x002b;
                        case 48: goto L_0x0032;
                        case 56: goto L_0x0039;
                        case 64: goto L_0x0040;
                        case 74: goto L_0x0047;
                        case 82: goto L_0x0087;
                        case 90: goto L_0x00c7;
                        case 96: goto L_0x00fb;
                        case 98: goto L_0x012f;
                        case 104: goto L_0x0171;
                        case 114: goto L_0x01ad;
                        case 117: goto L_0x0179;
                        default: goto L_0x0008;
                    };
                L_0x0008:
                    r0 = r6.a(r7, r0);
                    if (r0 != 0) goto L_0x0001;
                L_0x000e:
                    return r6;
                L_0x000f:
                    r0 = r7.readBytes();
                    r6.amw = r0;
                    goto L_0x0001;
                L_0x0016:
                    r0 = r7.readString();
                    r6.amx = r0;
                    goto L_0x0001;
                L_0x001d:
                    r2 = r7.readDouble();
                    r6.amy = r2;
                    goto L_0x0001;
                L_0x0024:
                    r0 = r7.readFloat();
                    r6.amz = r0;
                    goto L_0x0001;
                L_0x002b:
                    r2 = r7.nD();
                    r6.amA = r2;
                    goto L_0x0001;
                L_0x0032:
                    r0 = r7.nE();
                    r6.amB = r0;
                    goto L_0x0001;
                L_0x0039:
                    r0 = r7.nG();
                    r6.amC = r0;
                    goto L_0x0001;
                L_0x0040:
                    r0 = r7.nF();
                    r6.amD = r0;
                    goto L_0x0001;
                L_0x0047:
                    r0 = 74;
                    r2 = com.google.android.gms.internal.mh.b(r7, r0);
                    r0 = r6.amE;
                    if (r0 != 0) goto L_0x0073;
                L_0x0051:
                    r0 = r1;
                L_0x0052:
                    r2 = r2 + r0;
                    r2 = new com.google.android.gms.internal.lw.a[r2];
                    if (r0 == 0) goto L_0x005c;
                L_0x0057:
                    r3 = r6.amE;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x005c:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x0077;
                L_0x0061:
                    r3 = new com.google.android.gms.internal.lw$a;
                    r3.<init>();
                    r2[r0] = r3;
                    r3 = r2[r0];
                    r7.a(r3);
                    r7.nB();
                    r0 = r0 + 1;
                    goto L_0x005c;
                L_0x0073:
                    r0 = r6.amE;
                    r0 = r0.length;
                    goto L_0x0052;
                L_0x0077:
                    r3 = new com.google.android.gms.internal.lw$a;
                    r3.<init>();
                    r2[r0] = r3;
                    r0 = r2[r0];
                    r7.a(r0);
                    r6.amE = r2;
                    goto L_0x0001;
                L_0x0087:
                    r0 = 82;
                    r2 = com.google.android.gms.internal.mh.b(r7, r0);
                    r0 = r6.amF;
                    if (r0 != 0) goto L_0x00b3;
                L_0x0091:
                    r0 = r1;
                L_0x0092:
                    r2 = r2 + r0;
                    r2 = new com.google.android.gms.internal.lw.a.a[r2];
                    if (r0 == 0) goto L_0x009c;
                L_0x0097:
                    r3 = r6.amF;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x009c:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x00b7;
                L_0x00a1:
                    r3 = new com.google.android.gms.internal.lw$a$a;
                    r3.<init>();
                    r2[r0] = r3;
                    r3 = r2[r0];
                    r7.a(r3);
                    r7.nB();
                    r0 = r0 + 1;
                    goto L_0x009c;
                L_0x00b3:
                    r0 = r6.amF;
                    r0 = r0.length;
                    goto L_0x0092;
                L_0x00b7:
                    r3 = new com.google.android.gms.internal.lw$a$a;
                    r3.<init>();
                    r2[r0] = r3;
                    r0 = r2[r0];
                    r7.a(r0);
                    r6.amF = r2;
                    goto L_0x0001;
                L_0x00c7:
                    r0 = 90;
                    r2 = com.google.android.gms.internal.mh.b(r7, r0);
                    r0 = r6.amG;
                    if (r0 != 0) goto L_0x00ed;
                L_0x00d1:
                    r0 = r1;
                L_0x00d2:
                    r2 = r2 + r0;
                    r2 = new java.lang.String[r2];
                    if (r0 == 0) goto L_0x00dc;
                L_0x00d7:
                    r3 = r6.amG;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x00dc:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x00f1;
                L_0x00e1:
                    r3 = r7.readString();
                    r2[r0] = r3;
                    r7.nB();
                    r0 = r0 + 1;
                    goto L_0x00dc;
                L_0x00ed:
                    r0 = r6.amG;
                    r0 = r0.length;
                    goto L_0x00d2;
                L_0x00f1:
                    r3 = r7.readString();
                    r2[r0] = r3;
                    r6.amG = r2;
                    goto L_0x0001;
                L_0x00fb:
                    r0 = 96;
                    r2 = com.google.android.gms.internal.mh.b(r7, r0);
                    r0 = r6.amH;
                    if (r0 != 0) goto L_0x0121;
                L_0x0105:
                    r0 = r1;
                L_0x0106:
                    r2 = r2 + r0;
                    r2 = new long[r2];
                    if (r0 == 0) goto L_0x0110;
                L_0x010b:
                    r3 = r6.amH;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x0110:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x0125;
                L_0x0115:
                    r3 = r7.nD();
                    r2[r0] = r3;
                    r7.nB();
                    r0 = r0 + 1;
                    goto L_0x0110;
                L_0x0121:
                    r0 = r6.amH;
                    r0 = r0.length;
                    goto L_0x0106;
                L_0x0125:
                    r3 = r7.nD();
                    r2[r0] = r3;
                    r6.amH = r2;
                    goto L_0x0001;
                L_0x012f:
                    r0 = r7.nI();
                    r3 = r7.ex(r0);
                    r2 = r7.getPosition();
                    r0 = r1;
                L_0x013c:
                    r4 = r7.nN();
                    if (r4 <= 0) goto L_0x0148;
                L_0x0142:
                    r7.nD();
                    r0 = r0 + 1;
                    goto L_0x013c;
                L_0x0148:
                    r7.ez(r2);
                    r2 = r6.amH;
                    if (r2 != 0) goto L_0x0166;
                L_0x014f:
                    r2 = r1;
                L_0x0150:
                    r0 = r0 + r2;
                    r0 = new long[r0];
                    if (r2 == 0) goto L_0x015a;
                L_0x0155:
                    r4 = r6.amH;
                    java.lang.System.arraycopy(r4, r1, r0, r1, r2);
                L_0x015a:
                    r4 = r0.length;
                    if (r2 >= r4) goto L_0x016a;
                L_0x015d:
                    r4 = r7.nD();
                    r0[r2] = r4;
                    r2 = r2 + 1;
                    goto L_0x015a;
                L_0x0166:
                    r2 = r6.amH;
                    r2 = r2.length;
                    goto L_0x0150;
                L_0x016a:
                    r6.amH = r0;
                    r7.ey(r3);
                    goto L_0x0001;
                L_0x0171:
                    r2 = r7.nD();
                    r6.amJ = r2;
                    goto L_0x0001;
                L_0x0179:
                    r0 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
                    r2 = com.google.android.gms.internal.mh.b(r7, r0);
                    r0 = r6.amI;
                    if (r0 != 0) goto L_0x019f;
                L_0x0183:
                    r0 = r1;
                L_0x0184:
                    r2 = r2 + r0;
                    r2 = new float[r2];
                    if (r0 == 0) goto L_0x018e;
                L_0x0189:
                    r3 = r6.amI;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x018e:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x01a3;
                L_0x0193:
                    r3 = r7.readFloat();
                    r2[r0] = r3;
                    r7.nB();
                    r0 = r0 + 1;
                    goto L_0x018e;
                L_0x019f:
                    r0 = r6.amI;
                    r0 = r0.length;
                    goto L_0x0184;
                L_0x01a3:
                    r3 = r7.readFloat();
                    r2[r0] = r3;
                    r6.amI = r2;
                    goto L_0x0001;
                L_0x01ad:
                    r0 = r7.nI();
                    r2 = r7.ex(r0);
                    r3 = r0 / 4;
                    r0 = r6.amI;
                    if (r0 != 0) goto L_0x01d2;
                L_0x01bb:
                    r0 = r1;
                L_0x01bc:
                    r3 = r3 + r0;
                    r3 = new float[r3];
                    if (r0 == 0) goto L_0x01c6;
                L_0x01c1:
                    r4 = r6.amI;
                    java.lang.System.arraycopy(r4, r1, r3, r1, r0);
                L_0x01c6:
                    r4 = r3.length;
                    if (r0 >= r4) goto L_0x01d6;
                L_0x01c9:
                    r4 = r7.readFloat();
                    r3[r0] = r4;
                    r0 = r0 + 1;
                    goto L_0x01c6;
                L_0x01d2:
                    r0 = r6.amI;
                    r0 = r0.length;
                    goto L_0x01bc;
                L_0x01d6:
                    r6.amI = r3;
                    r7.ey(r2);
                    goto L_0x0001;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.a.t(com.google.android.gms.internal.ly):com.google.android.gms.internal.lw$a$a$a");
                }
            }

            public a() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r0 = this;
                r0.<init>();
                r0.nz();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.<init>():void");
            }

            public static com.google.android.gms.internal.lw.a.a[] ny() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r0 = amu;
                if (r0 != 0) goto L_0x0011;
            L_0x0004:
                r1 = com.google.android.gms.internal.mc.ana;
                monitor-enter(r1);
                r0 = amu;
                if (r0 != 0) goto L_0x0010;
            L_0x000b:
                r0 = 0;
                r0 = new com.google.android.gms.internal.lw.a.a[r0];
                amu = r0;
            L_0x0010:
                monitor-exit(r1);
            L_0x0011:
                r0 = amu;
                return r0;
            L_0x0014:
                r0 = move-exception;
                monitor-exit(r1);
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.ny():com.google.android.gms.internal.lw$a$a[]");
            }

            public void a(com.google.android.gms.internal.lz r3) throws java.io.IOException {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r2 = this;
                r0 = 1;
                r1 = r2.type;
                r3.p(r0, r1);
                r0 = r2.amv;
                if (r0 == 0) goto L_0x0010;
            L_0x000a:
                r0 = 2;
                r1 = r2.amv;
                r3.a(r0, r1);
            L_0x0010:
                super.a(r3);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.a(com.google.android.gms.internal.lz):void");
            }

            public /* synthetic */ com.google.android.gms.internal.me b(com.google.android.gms.internal.ly r2) throws java.io.IOException {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r1 = this;
                r0 = r1.s(r2);
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.b(com.google.android.gms.internal.ly):com.google.android.gms.internal.me");
            }

            protected int c() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r3 = this;
                r0 = super.c();
                r1 = 1;
                r2 = r3.type;
                r1 = com.google.android.gms.internal.lz.r(r1, r2);
                r0 = r0 + r1;
                r1 = r3.amv;
                if (r1 == 0) goto L_0x0018;
            L_0x0010:
                r1 = 2;
                r2 = r3.amv;
                r1 = com.google.android.gms.internal.lz.b(r1, r2);
                r0 = r0 + r1;
            L_0x0018:
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.c():int");
            }

            public boolean equals(java.lang.Object r5) {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r4 = this;
                r1 = 1;
                r0 = 0;
                if (r5 != r4) goto L_0x0006;
            L_0x0004:
                r0 = r1;
            L_0x0005:
                return r0;
            L_0x0006:
                r2 = r5 instanceof com.google.android.gms.internal.lw.a.a;
                if (r2 == 0) goto L_0x0005;
            L_0x000a:
                r5 = (com.google.android.gms.internal.lw.a.a) r5;
                r2 = r4.type;
                r3 = r5.type;
                if (r2 != r3) goto L_0x0005;
            L_0x0012:
                r2 = r4.amv;
                if (r2 != 0) goto L_0x0034;
            L_0x0016:
                r2 = r5.amv;
                if (r2 != 0) goto L_0x0005;
            L_0x001a:
                r2 = r4.amX;
                if (r2 == 0) goto L_0x0026;
            L_0x001e:
                r2 = r4.amX;
                r2 = r2.isEmpty();
                if (r2 == 0) goto L_0x003f;
            L_0x0026:
                r2 = r5.amX;
                if (r2 == 0) goto L_0x0032;
            L_0x002a:
                r2 = r5.amX;
                r2 = r2.isEmpty();
                if (r2 == 0) goto L_0x0005;
            L_0x0032:
                r0 = r1;
                goto L_0x0005;
            L_0x0034:
                r2 = r4.amv;
                r3 = r5.amv;
                r2 = r2.equals(r3);
                if (r2 != 0) goto L_0x001a;
            L_0x003e:
                goto L_0x0005;
            L_0x003f:
                r0 = r4.amX;
                r1 = r5.amX;
                r0 = r0.equals(r1);
                goto L_0x0005;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.equals(java.lang.Object):boolean");
            }

            public int hashCode() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r3 = this;
                r1 = 0;
                r0 = r3.type;
                r0 = r0 + 527;
                r2 = r0 * 31;
                r0 = r3.amv;
                if (r0 != 0) goto L_0x001d;
            L_0x000b:
                r0 = r1;
            L_0x000c:
                r0 = r0 + r2;
                r0 = r0 * 31;
                r2 = r3.amX;
                if (r2 == 0) goto L_0x001b;
            L_0x0013:
                r2 = r3.amX;
                r2 = r2.isEmpty();
                if (r2 == 0) goto L_0x0024;
            L_0x001b:
                r0 = r0 + r1;
                return r0;
            L_0x001d:
                r0 = r3.amv;
                r0 = r0.hashCode();
                goto L_0x000c;
            L_0x0024:
                r1 = r3.amX;
                r1 = r1.hashCode();
                goto L_0x001b;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.hashCode():int");
            }

            public com.google.android.gms.internal.lw.a.a nz() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r2 = this;
                r1 = 0;
                r0 = 1;
                r2.type = r0;
                r2.amv = r1;
                r2.amX = r1;
                r0 = -1;
                r2.anb = r0;
                return r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.nz():com.google.android.gms.internal.lw$a$a");
            }

            public com.google.android.gms.internal.lw.a.a s(com.google.android.gms.internal.ly r2) throws java.io.IOException {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r1 = this;
            L_0x0000:
                r0 = r2.nB();
                switch(r0) {
                    case 0: goto L_0x000d;
                    case 8: goto L_0x000e;
                    case 18: goto L_0x0019;
                    default: goto L_0x0007;
                };
            L_0x0007:
                r0 = r1.a(r2, r0);
                if (r0 != 0) goto L_0x0000;
            L_0x000d:
                return r1;
            L_0x000e:
                r0 = r2.nE();
                switch(r0) {
                    case 1: goto L_0x0016;
                    case 2: goto L_0x0016;
                    case 3: goto L_0x0016;
                    case 4: goto L_0x0016;
                    case 5: goto L_0x0016;
                    case 6: goto L_0x0016;
                    case 7: goto L_0x0016;
                    case 8: goto L_0x0016;
                    case 9: goto L_0x0016;
                    case 10: goto L_0x0016;
                    case 11: goto L_0x0016;
                    case 12: goto L_0x0016;
                    case 13: goto L_0x0016;
                    case 14: goto L_0x0016;
                    case 15: goto L_0x0016;
                    default: goto L_0x0015;
                };
            L_0x0015:
                goto L_0x0000;
            L_0x0016:
                r1.type = r0;
                goto L_0x0000;
            L_0x0019:
                r0 = r1.amv;
                if (r0 != 0) goto L_0x0024;
            L_0x001d:
                r0 = new com.google.android.gms.internal.lw$a$a$a;
                r0.<init>();
                r1.amv = r0;
            L_0x0024:
                r0 = r1.amv;
                r2.a(r0);
                goto L_0x0000;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a.s(com.google.android.gms.internal.ly):com.google.android.gms.internal.lw$a$a");
            }
        }

        public a() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = this;
            r0.<init>();
            r0.nx();
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.<init>():void");
        }

        public static com.google.android.gms.internal.lw.a[] nw() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = ams;
            if (r0 != 0) goto L_0x0011;
        L_0x0004:
            r1 = com.google.android.gms.internal.mc.ana;
            monitor-enter(r1);
            r0 = ams;
            if (r0 != 0) goto L_0x0010;
        L_0x000b:
            r0 = 0;
            r0 = new com.google.android.gms.internal.lw.a[r0];
            ams = r0;
        L_0x0010:
            monitor-exit(r1);
        L_0x0011:
            r0 = ams;
            return r0;
        L_0x0014:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.nw():com.google.android.gms.internal.lw$a[]");
        }

        public void a(com.google.android.gms.internal.lz r3) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r2 = this;
            r0 = 1;
            r1 = r2.name;
            r3.b(r0, r1);
            r0 = r2.amt;
            if (r0 == 0) goto L_0x0010;
        L_0x000a:
            r0 = 2;
            r1 = r2.amt;
            r3.a(r0, r1);
        L_0x0010:
            super.a(r3);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.a(com.google.android.gms.internal.lz):void");
        }

        public /* synthetic */ com.google.android.gms.internal.me b(com.google.android.gms.internal.ly r2) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
            r0 = r1.r(r2);
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.b(com.google.android.gms.internal.ly):com.google.android.gms.internal.me");
        }

        protected int c() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r3 = this;
            r0 = super.c();
            r1 = 1;
            r2 = r3.name;
            r1 = com.google.android.gms.internal.lz.h(r1, r2);
            r0 = r0 + r1;
            r1 = r3.amt;
            if (r1 == 0) goto L_0x0018;
        L_0x0010:
            r1 = 2;
            r2 = r3.amt;
            r1 = com.google.android.gms.internal.lz.b(r1, r2);
            r0 = r0 + r1;
        L_0x0018:
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.c():int");
        }

        public boolean equals(java.lang.Object r5) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r1 = 1;
            r0 = 0;
            if (r5 != r4) goto L_0x0006;
        L_0x0004:
            r0 = r1;
        L_0x0005:
            return r0;
        L_0x0006:
            r2 = r5 instanceof com.google.android.gms.internal.lw.a;
            if (r2 == 0) goto L_0x0005;
        L_0x000a:
            r5 = (com.google.android.gms.internal.lw.a) r5;
            r2 = r4.name;
            if (r2 != 0) goto L_0x0036;
        L_0x0010:
            r2 = r5.name;
            if (r2 != 0) goto L_0x0005;
        L_0x0014:
            r2 = r4.amt;
            if (r2 != 0) goto L_0x0041;
        L_0x0018:
            r2 = r5.amt;
            if (r2 != 0) goto L_0x0005;
        L_0x001c:
            r2 = r4.amX;
            if (r2 == 0) goto L_0x0028;
        L_0x0020:
            r2 = r4.amX;
            r2 = r2.isEmpty();
            if (r2 == 0) goto L_0x004c;
        L_0x0028:
            r2 = r5.amX;
            if (r2 == 0) goto L_0x0034;
        L_0x002c:
            r2 = r5.amX;
            r2 = r2.isEmpty();
            if (r2 == 0) goto L_0x0005;
        L_0x0034:
            r0 = r1;
            goto L_0x0005;
        L_0x0036:
            r2 = r4.name;
            r3 = r5.name;
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x0014;
        L_0x0040:
            goto L_0x0005;
        L_0x0041:
            r2 = r4.amt;
            r3 = r5.amt;
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x001c;
        L_0x004b:
            goto L_0x0005;
        L_0x004c:
            r0 = r4.amX;
            r1 = r5.amX;
            r0 = r0.equals(r1);
            goto L_0x0005;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.equals(java.lang.Object):boolean");
        }

        public int hashCode() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r3 = this;
            r1 = 0;
            r0 = r3.name;
            if (r0 != 0) goto L_0x0020;
        L_0x0005:
            r0 = r1;
        L_0x0006:
            r0 = r0 + 527;
            r2 = r0 * 31;
            r0 = r3.amt;
            if (r0 != 0) goto L_0x0027;
        L_0x000e:
            r0 = r1;
        L_0x000f:
            r0 = r0 + r2;
            r0 = r0 * 31;
            r2 = r3.amX;
            if (r2 == 0) goto L_0x001e;
        L_0x0016:
            r2 = r3.amX;
            r2 = r2.isEmpty();
            if (r2 == 0) goto L_0x002e;
        L_0x001e:
            r0 = r0 + r1;
            return r0;
        L_0x0020:
            r0 = r3.name;
            r0 = r0.hashCode();
            goto L_0x0006;
        L_0x0027:
            r0 = r3.amt;
            r0 = r0.hashCode();
            goto L_0x000f;
        L_0x002e:
            r1 = r3.amX;
            r1 = r1.hashCode();
            goto L_0x001e;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.hashCode():int");
        }

        public com.google.android.gms.internal.lw.a nx() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r2 = this;
            r1 = 0;
            r0 = "";
            r2.name = r0;
            r2.amt = r1;
            r2.amX = r1;
            r0 = -1;
            r2.anb = r0;
            return r2;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.nx():com.google.android.gms.internal.lw$a");
        }

        public com.google.android.gms.internal.lw.a r(com.google.android.gms.internal.ly r2) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r1 = this;
        L_0x0000:
            r0 = r2.nB();
            switch(r0) {
                case 0: goto L_0x000d;
                case 10: goto L_0x000e;
                case 18: goto L_0x0015;
                default: goto L_0x0007;
            };
        L_0x0007:
            r0 = r1.a(r2, r0);
            if (r0 != 0) goto L_0x0000;
        L_0x000d:
            return r1;
        L_0x000e:
            r0 = r2.readString();
            r1.name = r0;
            goto L_0x0000;
        L_0x0015:
            r0 = r1.amt;
            if (r0 != 0) goto L_0x0020;
        L_0x0019:
            r0 = new com.google.android.gms.internal.lw$a$a;
            r0.<init>();
            r1.amt = r0;
        L_0x0020:
            r0 = r1.amt;
            r2.a(r0);
            goto L_0x0000;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.lw.a.r(com.google.android.gms.internal.ly):com.google.android.gms.internal.lw$a");
        }
    }

    public lw() {
        nv();
    }

    public static lw n(byte[] bArr) throws md {
        return (lw) me.a(new lw(), bArr);
    }

    public void a(lz lzVar) throws IOException {
        if (this.amr != null && this.amr.length > 0) {
            for (me meVar : this.amr) {
                if (meVar != null) {
                    lzVar.a(1, meVar);
                }
            }
        }
        super.a(lzVar);
    }

    public /* synthetic */ me b(ly lyVar) throws IOException {
        return q(lyVar);
    }

    protected int c() {
        int c = super.c();
        if (this.amr != null && this.amr.length > 0) {
            for (me meVar : this.amr) {
                if (meVar != null) {
                    c += lz.b(1, meVar);
                }
            }
        }
        return c;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof lw)) {
            return false;
        }
        lw lwVar = (lw) o;
        if (!mc.equals(this.amr, lwVar.amr)) {
            return false;
        }
        if (this.amX == null || this.amX.isEmpty()) {
            return lwVar.amX == null || lwVar.amX.isEmpty();
        } else {
            return this.amX.equals(lwVar.amX);
        }
    }

    public int hashCode() {
        int hashCode = (mc.hashCode(this.amr) + 527) * 31;
        int hashCode2 = (this.amX == null || this.amX.isEmpty()) ? 0 : this.amX.hashCode();
        return hashCode2 + hashCode;
    }

    public lw nv() {
        this.amr = a.nw();
        this.amX = null;
        this.anb = -1;
        return this;
    }

    public lw q(ly lyVar) throws IOException {
        while (true) {
            int nB = lyVar.nB();
            switch (nB) {
                case 0:
                    break;
                case 10:
                    int b = mh.b(lyVar, 10);
                    nB = this.amr == null ? 0 : this.amr.length;
                    Object obj = new a[(b + nB)];
                    if (nB != 0) {
                        System.arraycopy(this.amr, 0, obj, 0, nB);
                    }
                    while (nB < obj.length - 1) {
                        obj[nB] = new a();
                        lyVar.a(obj[nB]);
                        lyVar.nB();
                        nB++;
                    }
                    obj[nB] = new a();
                    lyVar.a(obj[nB]);
                    this.amr = obj;
                    continue;
                default:
                    if (!a(lyVar, nB)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }
}
