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

import spock.lang.Specification

import com.tehlulz.ical.Reader.ReadException

import com.tehlulz.ical.Support.InviteTest

/**
 * <p>Tests {@link Reader}.</p>
 *
 * @author aslag
 */
class TestReader extends Specification {
  
  def "Reader successfully constructs objects for all 'complete' ical invites"() {
    when:
    def event = Reader.read(Support.load(invite))
    
    then:
    event
    
    where:
    invite << InviteTest.allComplete()
  }

  def "Reader constructor throws ReaderException if invite not valid ical"() {
    when:
    Reader.read("bogusbogus")

    then:
    thrown(ReadException)
  }

  def "IcalException is thrown during SingleEvent construction if ical contains no VEVENT entry"() {

    when:
    Reader.read(Support.load(InviteTest.invite_no_vevent))

    then:
    thrown(IcalException)
  }

  def "IcalException is thrown during SingleEvent construction if ical contains multiple VEVENT entries"() {
    when:
    Reader.read(Support.load(InviteTest.invite_1f29cbb39595_fc29c69c2084))

    then:
    thrown(IcalException)
  }
 
}
