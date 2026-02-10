package com.nine.ironladders.compat.modmenu;

import com.terraformersmc.modmenu.api.UpdateChannel;

public record VersionUpdateData(UpdateChannel updateChannel, String url, String version) {
}
