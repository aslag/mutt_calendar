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

import spock.lang.FailsWith
import spock.lang.Specification

import com.tehlulz.ical.Support.InviteTest

//TODO: fix tests that use one invite to use multiple invites as does "SingleEvent returns legitimate event description"
/**
 * <p>Tests {@link SingleEvent}</p>
 *
 * @author aslag
 */
class TestSingleEvent extends Specification {
  
  def "SingleEvent returns legitimate event description"() {
    when:
    SingleEvent event = Reader.read(Support.load(invite))

    then:
    event.description == description
    
    where:
    invite                          | description
    InviteTest.invite_4dc62c08d239  | "sample and discuss Uli's new album"
    InviteTest.invite_A85593CDC     | 'Purpose: Practice for league play\\N'
    InviteTest.invite_bc29c69c2083  | "Dude, I finally got the venue I wanted, I uh, I'm performing my dance quintet - you know, my cycle? - at Crane Jackson's Fountain Street Theater on Thursday night, and uh...I'd love it if you came and gave me notes."
  }

  def "SingleEvent returns legitimate event location"() {
    when:
    SingleEvent event = Reader.read(Support.load(invite))

    then:
    event.location == location
    
    where:
    invite                          | location
    InviteTest.invite_4dc62c08d239  | 'studio'
    InviteTest.invite_A85593CDC     | 'Holly Star Lanes'
    InviteTest.invite_bc29c69c2083  | 'Theater'
  }

  def "SingleEvent returns legitimate event summary"() {
    when:
    SingleEvent event = Reader.read(Support.load(invite))

    then:
    event.summary == summary
    
    where:
    invite                          | summary
    InviteTest.invite_4dc62c08d239  | 'invite'
    InviteTest.invite_A85593CDC     | 'bowling practice'
    InviteTest.invite_bc29c69c2083  | 'dance quintet'
  }
  
  def "SingleEvent returns empty String if no summary in ical data"() {
    when:
    SingleEvent event = Reader.read(Support.load(InviteTest.invite_ef45f77g3009))

    then:
    event.summary == ""
  }

  def "SingleEvent returns empty String if no description in ical data"() {
    when:
    SingleEvent event = Reader.read(Support.load(InviteTest.invite_ef45f77g3009))

    then:
    event.description == ""
  }
  
  def "SingleEvent returns accurate start date"() {
    when:
    SingleEvent event = Reader.read(Support.load(InviteTest.invite_bc29c69c2083))
    
    then:
    event.start.toString() == "20120710T153000"
  }

  def "SingleEvent returns accurate end date"() {
    when:
    SingleEvent event = Reader.read(Support.load(InviteTest.invite_bc29c69c2083))
    
    then:
    event.end.toString() == "20120710T163000"
  }

}
