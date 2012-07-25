/*
 * This file is part of ical_reader.
 *
 * ical_reader is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ical_reader is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ical_reader.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tehlulz.ical

import java.text.SimpleDateFormat

/**
 * <p>Contains static utility methods for use in reading and display data from 
 * ical files.</p>
 * 
 * @author aslag
 *
 */
class Utilities {

  public static final FORMAT_STRING = "K:mm a z 'on' EEEE MMMM d, yyyy"
  
  /**
   * <p>Strips given <code>propertyName</code> and ":" from front of given
   * <code>propertyLine</code> <code>String</code>.</p>
   *
   * @param propertyLine
   * @param propertyName
   * @return <code>String</code> <code>propertyLine</code> without prefix <code>
   * propertyName</code>
   * @throws ReadException In the case that the given <code>propertyName</code>
   * and ":" are not a prefix of the given <code>propertyLine</code>
   *
   */
  static String trimPropertyName(String propertyLine, String propertyName)
  throws IcalException {
    def matcher = (propertyLine =~ /^$propertyName:(.*)/)
    if (matcher.count == 0)
      throw new IcalException("Failed to match and trim line: " + propertyLine)
    matcher[0][1]
  }
  
  /**
   * <p>Formats and returns given <code>date</code> as a <code>String</code>
   * according to the member <code>FORMAT_STRING</code> in this class.</p>
   */
  static String formatDate(Date date)
  {
    SimpleDateFormat format = new SimpleDateFormat(FORMAT_STRING)
    format.format(date)
  } 
}
