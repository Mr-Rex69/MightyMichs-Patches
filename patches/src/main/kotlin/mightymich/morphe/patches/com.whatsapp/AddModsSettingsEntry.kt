package mightymich.morphe.patches.com.whatsapp

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.resourcePatch

/**
 * In-app English strings aligned with WhatsApp Settings tone
 * (short titles + one-line summaries, like Privacy / Chats rows).
 */
@Suppress("unused")
val addModsSettingsResourcesPatch = resourcePatch(
    name = "Mods settings resources",
    description = "Adds English strings and experimental-test consent copy for MightyMich settings.",
    default = true,
) {
    compatibleWith(WhatsAppCompatibility.WHATSAPP)

    execute {
        val file = get("res/values/strings.xml")
        if (!file.exists()) return@execute
        var xml = file.readText()
        if (xml.contains("mightymich_mods_title")) return@execute

        // WhatsApp-style: Title case row labels, sentence-case summaries.
        val extras = """
    <string name="mightymich_mods_title">MightyMich</string>
    <string name="mightymich_mods_summary">Extra privacy and media tools</string>
    <string name="mightymich_keep_deleted">Keep deleted messages</string>
    <string name="mightymich_keep_deleted_summary">Show messages that were deleted for everyone</string>
    <string name="mightymich_highlight_deleted">Highlight deleted messages</string>
    <string name="mightymich_highlight_deleted_summary">Mark deleted messages so they are easier to spot</string>
    <string name="mightymich_view_once">View once media</string>
    <string name="mightymich_view_once_summary">Experimental feature for controlled testing only</string>
    <string name="mightymich_view_once_consent_title">View Once Media — Test Mode</string>
    <string name="mightymich_view_once_consent_intro">This feature is experimental and intended for controlled testing.</string>
    <string name="mightymich_view_once_consent_permission">I confirm that I have permission from everyone involved to use the test media.</string>
    <string name="mightymich_view_once_consent_privacy">I agree to respect the privacy and consent of other participants.</string>
    <string name="mightymich_view_once_consent_responsibility">I understand that I am responsible for my use of the modified application.</string>
    <string name="mightymich_view_once_consent_risks">I understand that experimental patches may behave unexpectedly.</string>
    <string name="mightymich_view_once_consent_acknowledge">I have read and agree to these testing conditions.</string>
    <string name="mightymich_view_once_consent_continue">Continue to test mode</string>
    <string name="mightymich_view_once_consent_cancel">Cancel</string>
    <string name="mightymich_view_once_consent_notice">Acknowledgement does not replace the sender\'s consent or waive anyone else\'s rights.</string>
    <string name="mightymich_no_receipts">Read receipts</string>
    <string name="mightymich_no_receipts_summary">If turned off, you won\'t send read receipts</string>
    <string name="mightymich_hide_typing">Typing indicator</string>
    <string name="mightymich_hide_typing_summary">If turned off, others won\'t see when you are typing</string>
    <string name="mightymich_no_telemetry">Usage data</string>
    <string name="mightymich_no_telemetry_summary">Limit diagnostic and analytics data this app sends</string>
    <string name="mightymich_change_icon">App icon</string>
    <string name="mightymich_change_icon_summary">Choose a classic or premium-style launcher icon</string>
    <string name="mightymich_icon_blue">Blue</string>
    <string name="mightymich_icon_red">Red</string>
    <string name="mightymich_icon_gold">Gold premium</string>
    <string name="mightymich_mods_dialog_title">MightyMich</string>
    <string name="mightymich_mods_dialog_ok">OK</string>
    <string name="mightymich_mods_dialog_cancel">Cancel</string>
"""
        xml = xml.replace("</resources>", extras + "</resources>")
        file.writeText(xml)
    }
}

@Suppress("unused")
val addModsSettingsEntryPatch = bytecodePatch(
    name = "Mods settings entry",
    description = "Reserves a MightyMich row in Settings. Full dialog wiring needs the " +
        "settings-list fingerprint for this WhatsApp build.",
    default = true,
) {
    compatibleWith(WhatsAppCompatibility.WHATSAPP)
    dependsOn(addModsSettingsResourcesPatch)

    execute {
        // Settings list injection is version-specific (requires APK on disk).
    }
}
