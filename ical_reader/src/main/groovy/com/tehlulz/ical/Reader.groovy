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

import static net.fortuna.ical4j.util.CompatibilityHints.*
import groovy.transform.InheritConstructors
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.data.ParserException
import net.fortuna.ical4j.data.UnfoldingReader
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.util.CompatibilityHints

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
* <p>Delegates to {@link CalendarBuilder} to parse ical data given to
* constructor and makes available event details from that data.</p>
*
* @author aslag
*/
class Reader {

  private static final Logger LOG = LoggerFactory.getLogger(Reader.class);
  
  static SingleEvent read(String ical) {
    // use ical4j's unfolding reader to manage funny newlines
    UnfoldingReader unfoldingReader = new UnfoldingReader(new StringReader(ical))
    
    if (!CompatibilityHints.isHintEnabled(KEY_RELAXED_UNFOLDING))
      LOG.warn("KEY_RELAXED_UNFOLDING is not set and probably should be.")
    if (!CompatibilityHints.isHintEnabled(KEY_OUTLOOK_COMPATIBILITY))
      LOG.warn("KEY_OUTLOOK_COMPATIBILITY is not set and probably should be.")
    if (!CompatibilityHints.isHintEnabled(KEY_RELAXED_PARSING))
      LOG.warn("KEY_RELAXED_PARSING is not set and probably should be.")
    if (!CompatibilityHints.isHintEnabled(KEY_RELAXED_VALIDATION))
      LOG.warn("KEY_RELAXED_VALIDATION is not set and probably should be.")
    
    Calendar calendar
    try {
      calendar = new CalendarBuilder().build(unfoldingReader)
    } catch (IOException | ParserException ex) {
      LOG.error("Error parsing ical string <"+ical+">.", ex)
      throw new ReadException("Failed to build ical4j Calendar object.", ex)
    }

    try {
      new SingleEvent(calendar)
    } catch (IcalException ex) {
      LOG.error("Error instantiating SingleEvent from calendar object.", ex)
      throw new ReadException("Failed to build SingleEvent object.", ex)
    }
  }

  @InheritConstructors
  static class ReadException extends IcalException {
  }
}
