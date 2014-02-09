package io.ingramchen.association.social;

/**
 * represent the entity can be shared. any entity implements {@link ShareableEntity} requires call
 * {@link SocialDao#makeShareable(ShareableEntity)} before saving in db.
 * 
 * 
 * this interface maps to database table 'Shareable'
 * 
 * @author ingram
 * 
 */
public interface ShareableEntity
{
    String getShareableId();
}
