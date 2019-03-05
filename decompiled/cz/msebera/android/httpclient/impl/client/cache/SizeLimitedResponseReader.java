package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.client.cache.InputLimit;
import cz.msebera.android.httpclient.client.cache.Resource;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.message.BasicHttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;

@NotThreadSafe
class SizeLimitedResponseReader {
    private boolean consumed;
    private InputStream instream;
    private InputLimit limit;
    private final long maxResponseSizeBytes;
    private final HttpRequest request;
    private Resource resource;
    private final ResourceFactory resourceFactory;
    private final CloseableHttpResponse response;

    private void doConsume() throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r5 = this;
        r5.ensureNotConsumed();
        r2 = 1;
        r5.consumed = r2;
        r2 = new cz.msebera.android.httpclient.client.cache.InputLimit;
        r3 = r5.maxResponseSizeBytes;
        r2.<init>(r3);
        r5.limit = r2;
        r2 = r5.response;
        r0 = r2.getEntity();
        if (r0 != 0) goto L_0x0018;
    L_0x0017:
        return;
    L_0x0018:
        r2 = r5.request;
        r2 = r2.getRequestLine();
        r1 = r2.getUri();
        r2 = r0.getContent();
        r5.instream = r2;
        r2 = r5.resourceFactory;	 Catch:{ all -> 0x0042 }
        r3 = r5.instream;	 Catch:{ all -> 0x0042 }
        r4 = r5.limit;	 Catch:{ all -> 0x0042 }
        r2 = r2.generate(r1, r3, r4);	 Catch:{ all -> 0x0042 }
        r5.resource = r2;	 Catch:{ all -> 0x0042 }
        r2 = r5.limit;
        r2 = r2.isReached();
        if (r2 != 0) goto L_0x0017;
    L_0x003c:
        r2 = r5.instream;
        r2.close();
        goto L_0x0017;
    L_0x0042:
        r2 = move-exception;
        r3 = r5.limit;
        r3 = r3.isReached();
        if (r3 != 0) goto L_0x0050;
    L_0x004b:
        r3 = r5.instream;
        r3.close();
    L_0x0050:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.client.cache.SizeLimitedResponseReader.doConsume():void");
    }

    public SizeLimitedResponseReader(ResourceFactory resourceFactory, long maxResponseSizeBytes, HttpRequest request, CloseableHttpResponse response) {
        this.resourceFactory = resourceFactory;
        this.maxResponseSizeBytes = maxResponseSizeBytes;
        this.request = request;
        this.response = response;
    }

    protected void readResponse() throws IOException {
        if (!this.consumed) {
            doConsume();
        }
    }

    private void ensureNotConsumed() {
        if (this.consumed) {
            throw new IllegalStateException("Response has already been consumed");
        }
    }

    private void ensureConsumed() {
        if (!this.consumed) {
            throw new IllegalStateException("Response has not been consumed");
        }
    }

    boolean isLimitReached() {
        ensureConsumed();
        return this.limit.isReached();
    }

    Resource getResource() {
        ensureConsumed();
        return this.resource;
    }

    CloseableHttpResponse getReconstructedResponse() throws IOException {
        ensureConsumed();
        HttpResponse reconstructed = new BasicHttpResponse(this.response.getStatusLine());
        reconstructed.setHeaders(this.response.getAllHeaders());
        CombinedEntity combinedEntity = new CombinedEntity(this.resource, this.instream);
        HttpEntity entity = this.response.getEntity();
        if (entity != null) {
            combinedEntity.setContentType(entity.getContentType());
            combinedEntity.setContentEncoding(entity.getContentEncoding());
            combinedEntity.setChunked(entity.isChunked());
        }
        reconstructed.setEntity(combinedEntity);
        return (CloseableHttpResponse) Proxy.newProxyInstance(ResponseProxyHandler.class.getClassLoader(), new Class[]{CloseableHttpResponse.class}, new ResponseProxyHandler(reconstructed) {
            public void close() throws IOException {
                SizeLimitedResponseReader.this.response.close();
            }
        });
    }
}
