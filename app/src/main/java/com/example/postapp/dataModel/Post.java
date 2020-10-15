
package com.example.postapp.dataModel;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Post
{
  private String dateUpload;
  private String objectId;
  private String description;
  private String ownerId;
  private String photoUrl;
  private Date updated;
  private String nameUser;
  private Date created;
  private String profilePic;
  public String getDateUpload()
  {
    return dateUpload;
  }

  public void setDateUpload( String dateUpload )
  {

      SimpleDateFormat Format = new SimpleDateFormat("dd MMM yyyy ,hh:mm a", Locale.ENGLISH);
      this.dateUpload = Format.format(new Date(dateUpload));
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription( String description )
  {
    this.description = description;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getPhotoUrl()
  {
    return photoUrl;
  }

  public void setPhotoUrl( String photoUrl )
  {
    this.photoUrl = photoUrl;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public String getNameUser()
  {
    return nameUser;
  }

  public void setNameUser( String nameUser )
  {
    this.nameUser = nameUser;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getProfilePic()
  {
    return profilePic;
  }

  public void setProfilePic( String profilePic )
  {
    this.profilePic = profilePic;
  }

                                                    
  public Post save()
  {
    return Backendless.Data.of( Post.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Post> callback )
  {
    Backendless.Data.of( Post.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Post.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Post.class ).remove( this, callback );
  }

  public static Post findById( String id )
  {
    return Backendless.Data.of( Post.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Post> callback )
  {
    Backendless.Data.of( Post.class ).findById( id, callback );
  }

  public static Post findFirst()
  {
    return Backendless.Data.of( Post.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Post> callback )
  {
    Backendless.Data.of( Post.class ).findFirst( callback );
  }

  public static Post findLast()
  {
    return Backendless.Data.of( Post.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Post> callback )
  {
    Backendless.Data.of( Post.class ).findLast( callback );
  }

  public static List<Post> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Post.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Post>> callback )
  {
    Backendless.Data.of( Post.class ).find( queryBuilder, callback );
  }
}