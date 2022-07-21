package net.jems.somaticsorcery.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class FireballProjectileEntity extends AbstractFireballEntity {

    private int explosionPower;

    public FireballProjectileEntity(EntityType<? extends AbstractFireballEntity> entityType, World world, LivingEntity owner) {
        super(entityType, world);
        setVelocity(owner);
        explosionPower = 3;
    }

    public void setVelocity(LivingEntity owner) {
        setVelocity(owner, owner.getPitch(), owner.getYaw(), owner.getRoll(), 4, 0);
    }



    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
            this.world.createExplosion(null, this.getX(), this.getY(), this.getZ(), this.explosionPower,
                    bl, bl ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE);
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.world.isClient) {
            return;
        }
        Entity target = entityHitResult.getEntity();
        boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
        this.world.createExplosion(null, target.getX(), target.getY(), target.getZ(), this.explosionPower,
                bl, bl ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE);
        this.discard();
    }

    @Override
    public void tick() {
        HitResult hitResult;
        Entity entity = this.getOwner();
        if (!this.world.isClient && (entity != null && entity.isRemoved() || !this.world.isChunkLoaded(this.getBlockPos()))) {
            this.discard();
            return;
        }
        super.tick();
        if (this.isBurning()) {
            this.setOnFireFor(1);
        }
        if ((hitResult = ProjectileUtil.getCollision(this, this::canHit)).getType() != HitResult.Type.MISS) {
            this.onCollision(hitResult);
        }
        this.checkBlockCollision();
        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        ProjectileUtil.setRotationFromVelocity(this, 0.2f);
        float g = this.getDrag();
        if (this.isTouchingWater()) {
            for (int i = 0; i < 4; ++i) {
                float h = 0.25f;
                this.world.addParticle(ParticleTypes.BUBBLE, d - vec3d.x * 0.25, e - vec3d.y * 0.25, f - vec3d.z * 0.25, vec3d.x, vec3d.y, vec3d.z);
            }
            g = 1f;
        }
        //this.setVelocity(vec3d.add(this.powerX, this.powerY, this.powerZ).multiply(g));
        this.world.addParticle(this.getParticleType(), d, e + 0.5, f, 0.0, 0.0, 0.0);
        this.setPosition(d, e, f);
    }

}
