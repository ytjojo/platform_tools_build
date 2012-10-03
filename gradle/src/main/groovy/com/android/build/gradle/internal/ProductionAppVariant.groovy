/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.build.gradle.internal

import com.android.builder.AndroidBuilder
import com.android.builder.VariantConfiguration
import com.android.build.gradle.BasePlugin

class ProductionAppVariant extends ApplicationVariant {
    final String name

    ProductionAppVariant(VariantConfiguration config) {
        super(config)
        if (config.hasFlavors()) {
            name = "${getFlavoredName(true)}${config.buildType.name.capitalize()}"
        } else {
            name = "${config.buildType.name.capitalize()}"
        }
    }

    String getDescription() {
        if (config.hasFlavors()) {
            return "${config.buildType.name.capitalize()} build for flavor ${getFlavoredName(true)}"
        } else {
            return "${config.buildType.name.capitalize()} build"
        }
    }

    String getDirName() {
        if (config.hasFlavors()) {
            return "${getFlavoredName(false)}/$config.buildType.name"
        } else {
            return "$config.buildType.name"
        }
    }

    String getBaseName() {
        if (config.hasFlavors()) {
            return "${getFlavoredName(false)}-$config.buildType.name"
        } else {
            return "$config.buildType.name"
        }
    }

    @Override
    boolean getZipAlign() {
        return config.buildType.zipAlign
    }

    @Override
    boolean isSigned() {
        return config.buildType.debugSigned ||
                config.mergedFlavor.isSigningReady()
    }

    @Override
    boolean getRunProguard() {
        return config.buildType.runProguard
    }

    @Override
    List<String> getRunCommand() {
        throw new UnsupportedOperationException()
    }

    String getPackage() {
        return config.getPackageName()
    }

    @Override
    AndroidBuilder createBuilder(BasePlugin androidBasePlugin) {
        AndroidBuilder androidBuilder = new AndroidBuilder(
                androidBasePlugin.sdkParser,
                androidBasePlugin.logger,
                androidBasePlugin.verbose)

        androidBuilder.setTarget(androidBasePlugin.target)
        androidBuilder.setVariantConfig(config)

        return androidBuilder
    }
}
