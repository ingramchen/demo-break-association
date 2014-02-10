package io.ingramchen.association.model.social;

/**
 * represent the entity is likeable. any entity implements {@link LikeableEntity} requires call
 * {@link SocialDao#makeLikeable(LikeableEntity)} before saving in db.
 * 
 * 
 * @author ingram
 * 
 */
public interface LikeableEntity
{
    String getLikeableId();
}
