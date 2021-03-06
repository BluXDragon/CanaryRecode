package net.canarymod.api.entity.vehicle;


import net.minecraft.server.EntityMinecartHopper;


/**
 *
 * @author Somners
 */
public class CanaryHopperMinecart extends CanaryContainerMinecart implements HopperMinecart {

    public CanaryHopperMinecart(EntityMinecartHopper minecart) {
        super(minecart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPosX() {
        return this.getX();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPosY() {
        return this.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPosZ() {
        return this.getZ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTranferCooldown() {
        return this.getHandle().b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTransferCooldown(int cooldown) {
        this.getHandle().b = cooldown;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityMinecartHopper getHandle() {
        return (EntityMinecartHopper) this.entity;
    }
}
