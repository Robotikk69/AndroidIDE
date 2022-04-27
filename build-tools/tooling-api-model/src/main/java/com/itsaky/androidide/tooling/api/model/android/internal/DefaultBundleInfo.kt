/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.itsaky.androidide.tooling.api.model.android.internal

import com.android.builder.model.v2.ide.BundleInfo
import java.io.File

/** @author Akash Yadav */
class DefaultBundleInfo(
    override val apkFromBundleTaskName: String,
    override val apkFromBundleTaskOutputListingFile: File,
    override val bundleTaskName: String,
    override val bundleTaskOutputListingFile: File
) : BundleInfo {
    constructor() : this("", File("."), "", File("."))
    
    override fun toString(): String {
        return "DefaultBundleInfo(apkFromBundleTaskName='$apkFromBundleTaskName', apkFromBundleTaskOutputListingFile=$apkFromBundleTaskOutputListingFile, bundleTaskName='$bundleTaskName', bundleTaskOutputListingFile=$bundleTaskOutputListingFile)"
    }
}
