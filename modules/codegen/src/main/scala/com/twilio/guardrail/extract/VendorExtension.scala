package com.twilio.guardrail.extract

import io.swagger.v3.oas.models.media._
import io.swagger.v3.oas.models._
import io.swagger.v3.oas.models.parameters.{ CookieParameter, Parameter }
import io.swagger.v3.oas.models.responses.ApiResponse

object VendorExtension {
  trait VendorExtensible[-F] { //fixme -
    def extract[T](from: F, key: String)(implicit T: Extractable[T]): Option[T]
  }

  object VendorExtensible {

    def build[F](f: F => String => Any): VendorExtensible[F] =
      new VendorExtensible[F] {
        def extract[T](from: F, key: String)(implicit T: Extractable[T]): Option[T] =
          T.extract(f(from)(key)).toOption
      }

    implicit val defaultVendorExtensibleOperation: VendorExtensible[Operation] =
      build[Operation](m => key => m.getExtensions.get(key))

    implicit val defaultVendorExtensibleParameter: VendorExtensible[Parameter] =
      build[Parameter](m => key => m.getExtensions.get(key))

    implicit val defaultVendorExtensiblePath: VendorExtensible[PathItem] =
      build[PathItem](m => key => m.getExtensions.get(key))

//    implicit val defaultVendorExtensibleProperty: VendorExtensible[Property] =
//      build[Property](m => key => m.getVendorExtensions.get(key))

    implicit val defaultVendorExtensibleResponse: VendorExtensible[ApiResponse] =
      build[ApiResponse](m => key => m.getExtensions.get(key))

    implicit val defaultVendorExtensibleSwagger: VendorExtensible[OpenAPI] =
      build[OpenAPI](m => key => m.getExtensions.get(key))

    implicit val defaultVendorExtensibleSchemaProperty: VendorExtensible[Schema[_]] =
      build[Schema[_]](m => key => m.getExtensions.get(key))

//    implicit val defaultVendorExtensibleAbstractProperty: VendorExtensible[AbstractProperty] =
//      build[AbstractProperty](m => key => m.getVendorExtensions.get(key))

    implicit val defaultVendorExtensibleArrayProperty: VendorExtensible[ArraySchema] =
      build[ArraySchema](m => key => m.getExtensions.get(key))

//    implicit val defaultVendorExtensibleBaseIntegerProperty: VendorExtensible[BaseIntegerProperty] =
//      build[BaseIntegerProperty](m => key => m.getVendorExtensions.get(key))

    implicit val defaultVendorExtensibleBooleanProperty: VendorExtensible[BooleanSchema] =
      build[BooleanSchema](m => key => m.getExtensions.get(key))

    implicit val defaultVendorExtensibleDateProperty: VendorExtensible[DateSchema] =
      build[DateSchema](m => key => m.getExtensions.get(key))

    implicit val defaultVendorExtensibleDateTimeProperty: VendorExtensible[DateTimeSchema] =
      build[DateTimeSchema](m => key => m.getExtensions.get(key))

    implicit val defaultVendorExtensibleFloatProperty: VendorExtensible[NumberSchema] =
      build[NumberSchema](m => key => m.getExtensions.get(key))

    implicit val defaultVendorExtensibleIntegerProperty: VendorExtensible[IntegerSchema] =
      build[IntegerSchema](m => key => m.getExtensions.get(key))

//    implicit val defaultVendorExtensibleLongProperty: VendorExtensible[LongProperty] =
//      build[LongProperty](m => key => m.getVendorExtensions.get(key))

    implicit val defaultVendorExtensibleMapProperty: VendorExtensible[MapSchema] =
      build[MapSchema](m => key => m.getExtensions.get(key))

//    implicit val defaultVendorExtensibleModelImpl: VendorExtensible[ModelImpl] =
//      build[ModelImpl](m => key => m.getVendorExtensions.get(key))

    implicit val defaultVendorExtensibleObjectProperty: VendorExtensible[ObjectSchema] =
      build[ObjectSchema](m => key => m.getExtensions.get(key))

    implicit val defaultVendorExtensibleStringProperty: VendorExtensible[StringSchema] =
      build[StringSchema](m => key => m.getExtensions.get(key))

    implicit val defaultVendorExtensibleCookieParameter: VendorExtensible[CookieParameter] =
      build[CookieParameter](m => key => m.getExtensions.get(key))

//    implicit val defaultVendorExtensibleHeaderParameter: VendorExtensible[HeaderParameter] =
//      build[HeaderParameter](m => key => m.getVendorExtensions.get(key))
//    implicit val defaultVendorExtensiblePathParameter: VendorExtensible[PathParameter] =
//      build[PathParameter](m => key => m.getVendorExtensions.get(key))
//    implicit val defaultVendorExtensibleQueryParameter: VendorExtensible[QueryParameter] =
//      build[QueryParameter](m => key => m.getVendorExtensions.get(key))
//    implicit val defaultVendorExtensibleSerializableParameter: VendorExtensible[SerializableParameter] =
//      build[SerializableParameter](m => key => m.getVendorExtensions.get(key))
  }

  case class VendorExtensibleAdapter[F](from: F) extends AnyVal {
    def extract[T: Extractable](key: String)(implicit F: VendorExtensible[F]): Option[T] =
      F.extract(from, key)
  }

  def apply[F](from: F): VendorExtensibleAdapter[F] =
    VendorExtensibleAdapter(from)
}
