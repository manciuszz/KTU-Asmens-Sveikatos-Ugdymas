package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.view.MotionEventCompat;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public interface c extends IInterface {

    public static abstract class a extends Binder implements c {

        private static class a implements c {
            private IBinder kq;

            a(IBinder iBinder) {
                this.kq = iBinder;
            }

            public void a(float f, float f2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.kq.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean a(c cVar) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeStrongBinder(cVar != null ? cVar.asBinder() : null);
                    this.kq.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kq;
            }

            public float getBearing() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kq.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    float readFloat = obtain2.readFloat();
                    return readFloat;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public LatLngBounds getBounds() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kq.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    LatLngBounds createFromParcel = obtain2.readInt() != 0 ? LatLngBounds.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return createFromParcel;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getHeight() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kq.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    float readFloat = obtain2.readFloat();
                    return readFloat;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getId() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kq.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public LatLng getPosition() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kq.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    LatLng createFromParcel = obtain2.readInt() != 0 ? LatLng.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return createFromParcel;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getTransparency() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kq.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    float readFloat = obtain2.readFloat();
                    return readFloat;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getWidth() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kq.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    float readFloat = obtain2.readFloat();
                    return readFloat;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getZIndex() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kq.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    float readFloat = obtain2.readFloat();
                    return readFloat;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int hashCodeRemote() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kq.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isVisible() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kq.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void l(d dVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeStrongBinder(dVar != null ? dVar.asBinder() : null);
                    this.kq.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void remove() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kq.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setBearing(float bearing) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeFloat(bearing);
                    this.kq.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setDimensions(float width) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeFloat(width);
                    this.kq.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPosition(LatLng latlng) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    if (latlng != null) {
                        obtain.writeInt(1);
                        latlng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.kq.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPositionFromBounds(LatLngBounds bounds) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    if (bounds != null) {
                        obtain.writeInt(1);
                        bounds.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.kq.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setTransparency(float transparency) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeFloat(transparency);
                    this.kq.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setVisible(boolean visible) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    if (visible) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.kq.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setZIndex(float zIndex) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    obtain.writeFloat(zIndex);
                    this.kq.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static c aZ(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof c)) ? new a(iBinder) : (c) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            LatLngBounds latLngBounds = null;
            int i = 0;
            LatLng createFromParcel;
            float width;
            boolean isVisible;
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    remove();
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    String id = getId();
                    reply.writeNoException();
                    reply.writeString(id);
                    return true;
                case 3:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    if (data.readInt() != 0) {
                        createFromParcel = LatLng.CREATOR.createFromParcel(data);
                    }
                    setPosition(createFromParcel);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    createFromParcel = getPosition();
                    reply.writeNoException();
                    if (createFromParcel != null) {
                        reply.writeInt(1);
                        createFromParcel.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 5:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    setDimensions(data.readFloat());
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    a(data.readFloat(), data.readFloat());
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    width = getWidth();
                    reply.writeNoException();
                    reply.writeFloat(width);
                    return true;
                case 8:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    width = getHeight();
                    reply.writeNoException();
                    reply.writeFloat(width);
                    return true;
                case 9:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    if (data.readInt() != 0) {
                        latLngBounds = LatLngBounds.CREATOR.createFromParcel(data);
                    }
                    setPositionFromBounds(latLngBounds);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    latLngBounds = getBounds();
                    reply.writeNoException();
                    if (latLngBounds != null) {
                        reply.writeInt(1);
                        latLngBounds.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 11:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    setBearing(data.readFloat());
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    width = getBearing();
                    reply.writeNoException();
                    reply.writeFloat(width);
                    return true;
                case 13:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    setZIndex(data.readFloat());
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    width = getZIndex();
                    reply.writeNoException();
                    reply.writeFloat(width);
                    return true;
                case 15:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    setVisible(data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    isVisible = isVisible();
                    reply.writeNoException();
                    if (isVisible) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 17:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    setTransparency(data.readFloat());
                    reply.writeNoException();
                    return true;
                case MotionEventCompat.AXIS_RTRIGGER /*18*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    width = getTransparency();
                    reply.writeNoException();
                    reply.writeFloat(width);
                    return true;
                case 19:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    isVisible = a(aZ(data.readStrongBinder()));
                    reply.writeNoException();
                    if (isVisible) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 20:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    int hashCodeRemote = hashCodeRemote();
                    reply.writeNoException();
                    reply.writeInt(hashCodeRemote);
                    return true;
                case MotionEventCompat.AXIS_WHEEL /*21*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    l(com.google.android.gms.dynamic.d.a.ag(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(float f, float f2) throws RemoteException;

    boolean a(c cVar) throws RemoteException;

    float getBearing() throws RemoteException;

    LatLngBounds getBounds() throws RemoteException;

    float getHeight() throws RemoteException;

    String getId() throws RemoteException;

    LatLng getPosition() throws RemoteException;

    float getTransparency() throws RemoteException;

    float getWidth() throws RemoteException;

    float getZIndex() throws RemoteException;

    int hashCodeRemote() throws RemoteException;

    boolean isVisible() throws RemoteException;

    void l(d dVar) throws RemoteException;

    void remove() throws RemoteException;

    void setBearing(float f) throws RemoteException;

    void setDimensions(float f) throws RemoteException;

    void setPosition(LatLng latLng) throws RemoteException;

    void setPositionFromBounds(LatLngBounds latLngBounds) throws RemoteException;

    void setTransparency(float f) throws RemoteException;

    void setVisible(boolean z) throws RemoteException;

    void setZIndex(float f) throws RemoteException;
}
