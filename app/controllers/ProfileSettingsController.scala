package controllers

import java.io.File

import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ProfileSettingsController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Action to create Profile HTML file
   */
  def profileSettings() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.profilesettings())
  }
  def upload = Action(parse.multipartFormData) { request =>
    request.body
      .file("picture")
      .map { picture =>
        val filename = picture.filename
        val contentType = picture.contentType
        var replace = contentType.contains("image/png") || contentType.contains("image/jpeg")
        picture.ref.copyTo(new File(s"public/images/$filename"))
        Redirect(routes.ProfileController.profile()).flashing("Success" -> "Successful upload")
      }
      .getOrElse {
        Redirect(routes.ProfileController.profile()).flashing("error" -> "Missing file")
      }
  }

  def profilePicDelete = Action {
    Ok("TODO")

  }

  def updateProfilePic = Action(parse.multipartFormData) {
    request =>
      request.body.file("picture").map { picture =>
        val filename = "ProfilePic.png"
        val fileSize = picture.fileSize
        val contentType = picture.contentType
        var replace = contentType.contains("image/png") || contentType.contains("image/jpeg")
        picture.ref.copyTo(new File(s"public/images/profile-pictures/$filename"), replace)
        Redirect(routes.ProfileController.profile()).flashing("Success" -> "Successful upload")
      }
        .getOrElse {
          Redirect(routes.ProfileController.profile()).flashing("error" -> "Missing file")
        }
  }
}