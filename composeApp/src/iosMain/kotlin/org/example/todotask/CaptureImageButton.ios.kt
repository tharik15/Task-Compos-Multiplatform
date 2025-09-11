package org.example.todotask

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.StableRef
import platform.Foundation.NSData
import platform.Foundation.NSDate
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.writeToURL
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject
import platform.objc.OBJC_ASSOCIATION_RETAIN_NONATOMIC
import platform.objc.objc_AssociationPolicy
import platform.objc.objc_setAssociatedObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CaptureImageButton(
    controller: CameraController,
    onCaptured: (String?) -> Unit,
    modifier: Modifier
) {
    val delegateKey = StableRef.create("delegateKey").asCPointer()
    Button(onClick = {
        val picker = UIImagePickerController()
        picker.sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera

        val delegate = ImagePickerDelegate { image ->
            val path = image?.let { saveImageToDocuments(it) }
            onCaptured(path)
        }

        objc_setAssociatedObject(
            picker,  delegateKey, delegate,
            OBJC_ASSOCIATION_RETAIN_NONATOMIC
        )
        picker.delegate = delegate

        val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootVC?.presentViewController(picker, true, null)
    }, modifier = modifier){
        Text("Capture Photo")
    }
}

@ExportObjCClass
class ImagePickerDelegate(val callback: (UIImage?) -> Unit) :
    NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
    override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>
    ) {
        val image = didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
        picker.dismissViewControllerAnimated(true, null)
        callback(image)
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        picker.dismissViewControllerAnimated(true, null)
        callback(null)
    }
}

fun saveImageToDocuments(image: UIImage): String? {
    val data = UIImageJPEGRepresentation(image, 0.9) ?: return null
    val paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true)
    val documentsDirectory = paths.firstOrNull() as? String ?: return null
    val fileName = "evidence_${NSDate().timeIntervalSince1970}.jpg"
    val filePath = "$documentsDirectory/$fileName"
    val url = NSURL.fileURLWithPath(filePath)
    (data as NSData).writeToURL(url, true)
    return filePath
}