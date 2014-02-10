package io.ingramchen.association.model.social;

/**
 * represent the entity can be shared. any entity implements {@link ShareableEntity} requires call
 * {@link SocialDao#makeShareable(ShareableEntity)} before saving in db.
 * 
 * 
 * @author ingram
 * 
 */
public interface ShareableEntity
{
    String getShareableId();
}
