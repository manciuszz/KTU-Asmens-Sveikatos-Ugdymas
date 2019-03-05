package com.google.android.gms.tagmanager;

import android.util.LruCache;
import com.google.android.gms.tagmanager.l.a;

class bb<K, V> implements k<K, V> {
    private LruCache<K, V> agc;

    bb(int i, final a<K, V> aVar) {
        this.agc = new LruCache<K, V>(this, i) {
            final /* synthetic */ bb age;

            protected int sizeOf(K key, V value) {
                return aVar.sizeOf(key, value);
            }
        };
    }

    public void e(K k, V v) {
        this.agc.put(k, v);
    }

    public V get(K key) {
        return this.agc.get(key);
    }
}
