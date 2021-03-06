/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 wcm.io
 * %%
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
 * #L%
 */
package io.wcm.config.editor;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides available widget types and their default configurations
 */
public enum WidgetTypes {

  /**
   * Text Field widget. Following properties are available for configuration (all empty per default):
   * - required = true or false
   * - maxlength = integer value
   * - minlength = integer value
   * - pattern = regular expression for the value validation. E.g. /^[0-9]*$/
   */
  TEXTFIELD(DefaultWidgetConfiguration.TEXTFIELD),

  /**
   * Text Multifield widget, which allows to specifiy multiple values for a property.
   * Following properties are available for configuration (all empty per default):
   * - required = true or false
   * - maxlength = integer value
   * - minlength = integer value
   * - pattern = regular expression for the value validation. E.g. /^[0-9]*$/
   */
  TEXT_MULTIFIELD(DefaultWidgetConfiguration.MULTIFIELD),

  /**
   * Text Area widget. Following properties are available for configuration (all empty per default):
   * - requried = true or false
   * - maxlength = integer value
   * - minlength = integer value
   * - pattern = regular expression for the value validation. E.g. /^[0-9]*$/
   */
  TEXTAREA(DefaultWidgetConfiguration.TEXTAREA),

  /**
   * Path Browser widget with autocomplete function.Following properties are available for configuration (all empty per
   * default):
   * - required = true or false
   * - rootPath = root path from where pages can be selected. Default: /content
   */
  PATHBROWSER(DefaultWidgetConfiguration.PATHBROWSER),

  /**
   * Checkbox widget.Following properties are available for configuration (all empty per
   * default):
   * - required = true or false
   */
  CHECKBOX(DefaultWidgetConfiguration.CHECKBOX);


  private final Map<String, Object> params;


  WidgetTypes(Map<String, Object> confgurationParameters) {
    this.params = confgurationParameters;
  }

  /**
   * @param defaultOverrides
   * @return configuration properties of the widget with overridden default values
   */
  public Map<String, Object> getWidgetConfiguration(Map<String, Object> defaultOverrides) {
    Map<String, Object> parameters = new HashMap<>(this.params);
    for (Map.Entry<String, Object> entry : defaultOverrides.entrySet()) {
      parameters.put(entry.getKey(), entry.getValue());
    }
    return parameters;
  }

  /**
   * @return configuration properties of the widget
   */
  public Map<String, Object> getDefaultWidgetConfiguration() {
    return new HashMap<String, Object>(this.params);
  }

}
