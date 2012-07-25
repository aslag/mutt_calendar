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
package com.tehlulz.ical;

import static org.junit.Assert.*
import spock.lang.Specification

/**
 * <p>Tests {@link Utilities}</p>
 *
 * @author aslag
 */
class TestUtilities extends Specification {
  def "trimPropertyName trims as expected in legitimate cases"() {

    expect:
    Utilities.trimPropertyName(prefix+":"+content, prefix) == content

    where:
    prefix | content
    "PRE"  |  "sdfs"
    "PRE:" |  "sdfs"
    "FF"   |  "FF"
    ""     |  "cc"
    ""     |  ""
    " "    |  " "
    "!!%:" |  "::::"
  }

  def "trimPropertyName throws ReaderException if it fails to trim prefix"() {
    def prefix = "PRE"
    def content = "sdfsdf"

    when:
    Utilities.trimPropertyName(prefix+content, prefix)

    then:
    thrown(IcalException)
  }
}
