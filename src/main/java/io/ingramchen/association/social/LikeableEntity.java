package io.ingramchen.association.social;

/**
 * represent the entity is likeable. any entity implements {@link LikeableEntity} requires call
 * {@link SocialDao#makeLikeable(LikeableEntity)} before saving in db.
 * 
 * 
 * this interface maps to database table 'Likeable'
 * 
 * @author ingram
 * 
 */
public interface LikeableEntity
{
    String getLikeableId();
}
