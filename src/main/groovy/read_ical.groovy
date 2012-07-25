#!/bin/env groovy

/*
 * This file is part of mutt_calendar.
 *
 * mutt_calendar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mutt_calendar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mutt_calendar.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.tehlulz.ical.SingleEvent
import com.tehlulz.ical.Reader
import com.tehlulz.ical.Utilities

if (args.size() < 1) {
  println "Usage: java -jar mutt_calendar.jar path_to_ical_file"
  System.exit(1)
}

try {
  SingleEvent se = Reader.read(new File(args[0]).text)
  def start = Utilities.formatDate(se.start)
  def end = Utilities.formatDate(se.end)
  println "    +-------------------------------------------------------------------------------+"
  if (se.summary) {
    println "         Summary:      $se.summary"
    println "    +-------------------------------------------------------------------------------+"
  }
  if (se.location)
    println "         Location:     $se.location"
  println "         Start:        $start"
  println "         End:          $end"
  println "    +-------------------------------------------------------------------------------+"
} catch (Exception ex) {
  println "    Error reading ical file; see error log for details.   "
} 