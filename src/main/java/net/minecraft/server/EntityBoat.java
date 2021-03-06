package net.minecraft.server;


import java.util.List;
import net.canarymod.Canary;
import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.entity.living.EntityLiving;
import net.canarymod.api.entity.vehicle.CanaryBoat;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.api.world.position.Vector3D;
import net.canarymod.hook.CancelableHook;
import net.canarymod.hook.entity.VehicleCollisionHook;
import net.canarymod.hook.entity.VehicleDamageHook;
import net.canarymod.hook.entity.VehicleDestroyHook;
import net.canarymod.hook.entity.VehicleEnterHook;
import net.canarymod.hook.entity.VehicleExitHook;
import net.canarymod.hook.entity.VehicleMoveHook;


public class EntityBoat extends Entity {

    private boolean a;
    private double b;
    private int c;
    private double d;
    private double e;
    private double f;
    private double g;
    private double h;

    public EntityBoat(World world) {
        super(world);
        this.a = true;
        this.b = 0.07D;
        this.m = true;
        this.a(1.5F, 0.6F);
        this.N = this.P / 2.0F;
        this.entity = new CanaryBoat(this); // CanaryMod: Wrap Entity
    }

    protected boolean f_() {
        return false;
    }

    protected void a() {
        this.ah.a(17, new Integer(0));
        this.ah.a(18, new Integer(1));
        this.ah.a(19, new Integer(0));
    }

    public AxisAlignedBB g(Entity entity) {
        return entity.E;
    }

    public AxisAlignedBB D() {
        return this.E;
    }

    public boolean L() {
        return true;
    }

    public EntityBoat(World world, double d0, double d1, double d2) {
        this(world);
        this.b(d0, d1 + (double) this.N, d2);
        this.x = 0.0D;
        this.y = 0.0D;
        this.z = 0.0D;
        this.r = d0;
        this.s = d1;
        this.t = d2;
        this.entity = new CanaryBoat(this); // CanaryMod: Wrap Entity
    }

    public double W() {
        return (double) this.P * 0.0D - 0.30000001192092896D;
    }

    public boolean a(DamageSource damagesource, int i0) {
        if (this.aq()) {
            return false;
        } else if (!this.q.I && !this.M) {
            // CanaryMod: VehicleDamage
            net.canarymod.api.entity.Entity attk = null;

            if (damagesource.h() != null) {
                attk = damagesource.h().getCanaryEntity();
            }
            VehicleDamageHook hook = new VehicleDamageHook((CanaryBoat) this.entity, attk, new CanaryDamageSource(damagesource), i0);

            Canary.hooks().callHook(hook);
            if (hook.isCanceled()) {
                return false;
            }
            i0 = hook.getDamageDealt();
            //

            this.h(-this.h());
            this.b(10);
            this.a(this.d() + i0 * 10);
            this.J();
            boolean flag0 = damagesource.i() instanceof EntityPlayer && ((EntityPlayer) damagesource.i()).ce.d;

            if (flag0 || this.d() > 40) {
                if (this.n != null) {
                    this.n.a((Entity) this);
                }

                if (!flag0) {
                    this.a(Item.aF.cp, 1, 0.0F);
                }
                // CanaryMod: VehicleDestroy
                VehicleDestroyHook vdh = new VehicleDestroyHook((Vehicle) this.entity);
                Canary.hooks().callHook(vdh);
                //
                this.w();
            }

            return true;
        } else {
            return true;
        }
    }

    public boolean K() {
        return !this.M;
    }

    public void l_() {
        super.l_();
        if (this.g() > 0) {
            this.b(this.g() - 1);
        }

        if (this.d() > 0) {
            this.a(this.d() - 1);
        }

        this.r = this.u;
        this.s = this.v;
        this.t = this.w;
        byte b0 = 5;
        double d0 = 0.0D;

        for (int i0 = 0; i0 < b0; ++i0) {
            double d1 = this.E.b + (this.E.e - this.E.b) * (double) (i0 + 0) / (double) b0 - 0.125D;
            double d2 = this.E.b + (this.E.e - this.E.b) * (double) (i0 + 1) / (double) b0 - 0.125D;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.a().a(this.E.a, d1, this.E.c, this.E.d, d2, this.E.f);

            if (this.q.b(axisalignedbb, Material.h)) {
                d0 += 1.0D / (double) b0;
            }
        }

        double d3 = Math.sqrt(this.x * this.x + this.z * this.z);
        double d4;
        double d5;

        if (d3 > 0.26249999999999996D) {
            d4 = Math.cos((double) this.A * 3.141592653589793D / 180.0D);
            d5 = Math.sin((double) this.A * 3.141592653589793D / 180.0D);

            for (int i1 = 0; (double) i1 < 1.0D + d3 * 60.0D; ++i1) {
                double d6 = (double) (this.ab.nextFloat() * 2.0F - 1.0F);
                double d7 = (double) (this.ab.nextInt(2) * 2 - 1) * 0.7D;
                double d8;
                double d9;

                if (this.ab.nextBoolean()) {
                    d8 = this.u - d4 * d6 * 0.8D + d5 * d7;
                    d9 = this.w - d5 * d6 * 0.8D - d4 * d7;
                    this.q.a("splash", d8, this.v - 0.125D, d9, this.x, this.y, this.z);
                } else {
                    d8 = this.u + d4 + d5 * d6 * 0.7D;
                    d9 = this.w + d5 - d4 * d6 * 0.7D;
                    this.q.a("splash", d8, this.v - 0.125D, d9, this.x, this.y, this.z);
                }
            }
        }

        double d10;
        double d11;

        if (this.q.I && this.a) {
            if (this.c > 0) {
                d4 = this.u + (this.d - this.u) / (double) this.c;
                d5 = this.v + (this.e - this.v) / (double) this.c;
                d10 = this.w + (this.f - this.w) / (double) this.c;
                d11 = MathHelper.g(this.g - (double) this.A);
                this.A = (float) ((double) this.A + d11 / (double) this.c);
                this.B = (float) ((double) this.B + (this.h - (double) this.B) / (double) this.c);
                --this.c;
                this.b(d4, d5, d10);
                this.b(this.A, this.B);
            } else {
                d4 = this.u + this.x;
                d5 = this.v + this.y;
                d10 = this.w + this.z;
                this.b(d4, d5, d10);
                if (this.F) {
                    this.x *= 0.5D;
                    this.y *= 0.5D;
                    this.z *= 0.5D;
                }

                this.x *= 0.9900000095367432D;
                this.y *= 0.949999988079071D;
                this.z *= 0.9900000095367432D;
            }
        } else {
            if (d0 < 1.0D) {
                d4 = d0 * 2.0D - 1.0D;
                this.y += 0.03999999910593033D * d4;
            } else {
                if (this.y < 0.0D) {
                    this.y /= 2.0D;
                }

                this.y += 0.007000000216066837D;
            }

            if (this.n != null) {
                this.x += this.n.x * this.b;
                this.z += this.n.z * this.b;
            }

            d4 = Math.sqrt(this.x * this.x + this.z * this.z);
            if (d4 > 0.35D) {
                d5 = 0.35D / d4;
                this.x *= d5;
                this.z *= d5;
                d4 = 0.35D;
            }

            if (d4 > d3 && this.b < 0.35D) {
                this.b += (0.35D - this.b) / 35.0D;
                if (this.b > 0.35D) {
                    this.b = 0.35D;
                }
            } else {
                this.b -= (this.b - 0.07D) / 35.0D;
                if (this.b < 0.07D) {
                    this.b = 0.07D;
                }
            }

            if (this.F) {
                this.x *= 0.5D;
                this.y *= 0.5D;
                this.z *= 0.5D;
            }

            this.d(this.x, this.y, this.z);
            if (this.G && d3 > 0.2D) {
                if (!this.q.I && !this.M) {
                    this.w();

                    int i2;

                    for (i2 = 0; i2 < 3; ++i2) {
                        this.a(Block.B.cz, 1, 0.0F);
                    }

                    for (i2 = 0; i2 < 2; ++i2) {
                        this.a(Item.E.cp, 1, 0.0F);
                    }
                }
            } else {
                this.x *= 0.9900000095367432D;
                this.y *= 0.949999988079071D;
                this.z *= 0.9900000095367432D;
            }

            this.B = 0.0F;
            d5 = (double) this.A;
            d10 = this.r - this.u;
            d11 = this.t - this.w;
            if (d10 * d10 + d11 * d11 > 0.001D) {
                d5 = (double) ((float) (Math.atan2(d11, d10) * 180.0D / 3.141592653589793D));
            }

            double d12 = MathHelper.g(d5 - (double) this.A);

            if (d12 > 20.0D) {
                d12 = 20.0D;
            }

            if (d12 < -20.0D) {
                d12 = -20.0D;
            }

            this.A = (float) ((double) this.A + d12);
            this.b(this.A, this.B);
            // CanaryMod: VehicleMove
            if (Math.floor(this.r) != Math.floor(this.u) || Math.floor(this.s) != Math.floor(this.v) || Math.floor(this.t) != Math.floor(this.w)) {
                Vector3D from = new Vector3D(this.r, this.s, this.t);
                Vector3D to = new Vector3D(this.u, this.v, this.w);
                VehicleMoveHook vmh = new VehicleMoveHook((Vehicle) this.entity, from, to);

                Canary.hooks().callHook(vmh);
                // Can't handle canceling yet...
            }
            //
            if (!this.q.I) {
                List list = this.q.b((Entity) this, this.E.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));
                int i3;

                if (list != null && !list.isEmpty()) {
                    for (i3 = 0; i3 < list.size(); ++i3) {
                        Entity entity = (Entity) list.get(i3);

                        if (entity != this.n && entity.L() && entity instanceof EntityBoat) {
                            // CanaryMod: VehicleCollision
                            VehicleCollisionHook vch = new VehicleCollisionHook((Vehicle) this.entity, entity.getCanaryEntity());

                            Canary.hooks().callHook(vch);
                            if (!vch.isCanceled()) {
                                entity.f((Entity) this);
                            }
                            //
                        }
                    }
                }

                for (i3 = 0; i3 < 4; ++i3) {
                    int i4 = MathHelper.c(this.u + ((double) (i3 % 2) - 0.5D) * 0.8D);
                    int i5 = MathHelper.c(this.w + ((double) (i3 / 2) - 0.5D) * 0.8D);

                    for (int i6 = 0; i6 < 2; ++i6) {
                        int i7 = MathHelper.c(this.v) + i6;
                        int i8 = this.q.a(i4, i7, i5);

                        if (i8 == Block.aW.cz) {
                            this.q.i(i4, i7, i5);
                        } else if (i8 == Block.bD.cz) {
                            this.q.a(i4, i7, i5, true);
                        }
                    }
                }

                if (this.n != null && this.n.M) {
                    this.n = null;
                }
            }
        }
    }

    public void U() {
        if (this.n != null) {
            double d0 = Math.cos((double) this.A * 3.141592653589793D / 180.0D) * 0.4D;
            double d1 = Math.sin((double) this.A * 3.141592653589793D / 180.0D) * 0.4D;

            this.n.b(this.u + d0, this.v + this.W() + this.n.V(), this.w + d1);
        }
    }

    protected void b(NBTTagCompound nbttagcompound) {}

    protected void a(NBTTagCompound nbttagcompound) {}

    public boolean a_(EntityPlayer entityplayer) {
        if (this.n != null && this.n instanceof EntityPlayer && this.n != entityplayer) {
            return true;
        } else {
            if (!this.q.I) {
                // CanaryMod: VehicleEnter/VehicleExit
                CancelableHook hook = null;

                if (this.n == null) {
                    hook = new VehicleEnterHook((Vehicle) this.entity, (EntityLiving) entityplayer.getCanaryEntity());
                } else if (this.n == entityplayer) {
                    hook = new VehicleExitHook((Vehicle) this.entity, (EntityLiving) entityplayer.getCanaryEntity());
                }
                if (hook != null) {
                    Canary.hooks().callHook(hook);
                    if (!hook.isCanceled()) {
                        entityplayer.a((Entity) this);
                    }
                }
                //
            }

            return true;
        }
    }

    public void a(int i0) {
        this.ah.b(19, Integer.valueOf(i0));
    }

    public int d() {
        return this.ah.c(19);
    }

    public void b(int i0) {
        this.ah.b(17, Integer.valueOf(i0));
    }

    public int g() {
        return this.ah.c(17);
    }

    public void h(int i0) {
        this.ah.b(18, Integer.valueOf(i0));
    }

    public int h() {
        return this.ah.c(18);
    }
}
