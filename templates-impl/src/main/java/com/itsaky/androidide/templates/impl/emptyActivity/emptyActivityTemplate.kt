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

package com.itsaky.androidide.templates.impl.emptyActivity

import com.itsaky.androidide.templates.Language
import com.itsaky.androidide.templates.ProjectTemplate
import com.itsaky.androidide.templates.base.AndroidModuleTemplateBuilder
import com.itsaky.androidide.templates.base.baseProject
import com.itsaky.androidide.templates.base.modules.android.defaultAppModule
import com.itsaky.androidide.templates.base.util.SourceWriter

fun emptyActivityProject(): ProjectTemplate = baseProject {
  defaultAppModule {
    recipe = {
      sources {
        writeEmptyActivity(this)
      }
    }
  }
}

internal fun AndroidModuleTemplateBuilder.writeEmptyActivity(writer: SourceWriter) {
  writer.apply {
    if (data.language == Language.Kotlin) {
      writeKtSrc(data.packageName, "MainActivity",
        source = this@writeEmptyActivity::emptyActivityKtSrc)
    } else {
      writeJavaSrc(packageName = data.packageName, className = "MainActivity",
        source = this@writeEmptyActivity::emptyActivityJavaSrc)
    }
  }
}

private fun AndroidModuleTemplateBuilder.emptyActivityKtSrc(): String {
  return """
package ${data.packageName}

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ${data.packageName}.databinding.ActivityMainBinding

public class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    
    private val binding: ActivityMainBinding
      get() = checkNotNull(_binding) { "Activity has been destroyed" }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate and get instance of binding
        _binding = ActivityMainBinding.inflate(layoutInflater)

        // set content view to binding's root
        setContentView(binding.root)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
"""
}

private fun AndroidModuleTemplateBuilder.emptyActivityJavaSrc(): String {
  return """
package ${data.packageName};

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import ${data.packageName}.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
"""
}
