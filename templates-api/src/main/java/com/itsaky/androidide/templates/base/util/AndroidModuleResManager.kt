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

package com.itsaky.androidide.templates.base.util

import com.android.aaptcompiler.ConfigDescription
import com.android.xml.XmlBuilder
import com.itsaky.androidide.templates.SrcSet
import com.itsaky.androidide.templates.base.AndroidModuleTemplateBuilder
import java.io.File

/**
 * Handles creation of XML files in an Android module template.
 *
 * @author Akash Yadav
 */
class AndroidModuleResManager {

  enum class ResourceType(val dirName: String) { ANIM("anim"),
    ANIMATOR("animator"),
    COLOR("color"),
    DRAWABLE("drawable"),
    FONT("font"),
    LAYOUT("layout"),
    MENU("menu"),
    MIPMAP("mipmap"),
    NAVIGATION("navigation"),
    VALUES("values"),
  }

  /**
   * Get the resource directory the given [resource type][ResourceType] and the [configuration][ConfigDescription].
   *
   * @param type The resource type.
   * @param srcSet The source set.
   * @param config The configuration for the resource type.
   */
  fun AndroidModuleTemplateBuilder.resDir(type: ResourceType, srcSet: SrcSet,
                                          config: ConfigDescription = ConfigDescription()
  ): File {
    var name = type.dirName
    config.toString().also {
      if (it != "DEFAULT") {
        name += it
      }
    }
    return File(resDir(srcSet), name).also { it.mkdirs() }
  }

  /**
   * Create a new XML resource file for the given [resource type][ResourceType] and the [configuration][ConfigDescription].
   *
   * @param name The name of the resource without the `.xml` extension.
   * @param type The resource type.
   * @param srcSet The source set.
   * @param config The configuration for the resource type.
   */
  fun AndroidModuleTemplateBuilder.createXmlResource(name: String,
                                                     type: ResourceType,
                                                     srcSet: SrcSet = SrcSet.Main,
                                                     config: ConfigDescription = ConfigDescription(),
                                                     configure: XmlBuilder.() -> Unit = {}
  ) {
    val file = File(resDir(type, srcSet, config), "${name}.xml")
    val builder = XmlBuilder().apply(configure)
    executor.save(builder.withXmlDecl(), file)
  }

  /**
   * Create a new XML resource file for the given [resource type][ResourceType] and the [configuration][ConfigDescription] with the given [source].
   *
   * @param name The name of the resource without the `.xml` extension.
   * @param type The resource type.
   * @param srcSet The source set.
   * @param config The configuration for the resource type.
   * @param source The source code for the resource.
   */
  fun AndroidModuleTemplateBuilder.writeXmlResource(name: String,
                                                    type: ResourceType,
                                                    srcSet: SrcSet = SrcSet.Main,
                                                    config: ConfigDescription = ConfigDescription(),
                                                    source: String
  ) {
    val file = File(resDir(type, srcSet, config), "${name}.xml")
    executor.save(source, file)
  }

  /**
   * Create a new XML resource file for the given [resource type][ResourceType] and the [configuration][ConfigDescription] with the given [source].
   *
   * @param name The name of the resource without the `.xml` extension.
   * @param type The resource type.
   * @param srcSet The source set.
   * @param config The configuration for the resource type.
   * @param source Function which returns the source code for the resource.
   */
  fun AndroidModuleTemplateBuilder.writeXmlResource(name: String,
                                                    type: ResourceType,
                                                    srcSet: SrcSet = SrcSet.Main,
                                                    config: ConfigDescription = ConfigDescription(),
                                                    source: () -> String = { "" }
  ) {
    writeXmlResource(name, type, srcSet, config, source())
  }
}