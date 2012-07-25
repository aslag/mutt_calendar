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

/**
 * <p>Containts stuff that supports unit tests including a method to load test
 * files from the classpath.</p>
 *
 * @author aslag
 *
 */
class Support
{
  
  enum InviteTest {
    
    invite_4dc62c08d239("4dc62c08d239.ical", true, "..", Vendor.THUNDERBIRD),
    invite_A85593CDC("A85593CDC.ical", true, "single event, multiple attendees; MS Exchange / 2.0", Vendor.OUTLOOK),
    invite_bc29c69c2083("bc29c69c2083.ical", true, "single vevent, multiple attendees; data on line splits so requires flexible parsing", Vendor.THUNDERBIRD),
    invite_ef45f77g3009("ef45f77g3009.ical", true, "single vevent, single attendee, no summary, no description", Vendor.THUNDERBIRD),
    invite_1f29cbb39595_fc29c69c2084("1f29cbb39595_fc29c69c2084.ical", false, "multiple vevents", Vendor.THUNDERBIRD),
    invite_no_vevent("invite_no_vevent.ical", false, "legitimate ical, no vevent or timezone components", Vendor.THUNDERBIRD),
    invite_winmail_dat("invite_winmail.dat", false, "enclosed in MS proprietary shit attachment format", Vendor.OUTLOOK)
    
    enum Vendor {
      THUNDERBIRD, OUTLOOK;
    }
    
    final String fname
    final Boolean isComplete
    final String description
    final Vendor vendor
   
    //TODO: groovy wouldn't create this constructor automatically like it does with regular classes; why?
    public InviteTest(String fname, Boolean isComplete, String description, Vendor vendor) {
      this.fname = fname
      this.isComplete = isComplete
      this.description = description
      this.vendor = vendor
    }
    
    public static def byVendor(Vendor vendor) {
      InviteTest.findAll {
        it.vendor == vendor
      }
    }
    
    public static def allComplete() {
      InviteTest.findAll {
        it.isComplete
      }
    }
  }
  
  /**
   *  Loads given <code>invite</code> using the classloader for this test class
   *  and returns a <code>String</code> representation of it.
   *
   * @paramn invite 
   * @return <code>String</code>
   */
  static String load(InviteTest invite) {
    Class thisClass = Support.class
    
    ClassLoader cl  
    if ((cl = thisClass.getClassLoader()) == null) // !!the class must be named (rather than using "this.getClass()" b/c of groovy dynamic compilation in eclipse
      throw new Exception("The classloader for class used to load InviteTest files from the fs is null and can't be.")
   
    URL inviteUrl
    if ((inviteUrl = cl.getResource(invite.fname)) == null)
      throw new Exception("Unable to find resource w/ name "+ invite.fname +" using classloader of class " + thisClass.name)
        
    new File(cl.getResource(invite.fname).toURI()).text
  }
}
