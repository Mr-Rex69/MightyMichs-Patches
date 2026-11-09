package mightymich.morphe.patches.com.whatsapp

import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch

@Suppress("unused")
val viewOnceUnlockPatch = bytecodePatch(
    name = "View once media",
    description = "Not compatible with the currently analysed WhatsApp build.",
    default = false,
) {
    compatibleWith(WhatsAppCompatibility.WHATSAPP)

    execute {
        throw PatchException(
            "WhatsApp View once media: this build's viewer implementation has not been verified. " +
                "The previous generic matcher found no valid gates, so the patch is disabled " +
                "rather than applying an unsafe or incorrect bytecode modification.",
        )
    }
}
