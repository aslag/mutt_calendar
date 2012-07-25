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

import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.data.ParserException
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.Property

/**
 * <p>This class' constructor takes a {@link Calendar} object with ical data 
 * containing a single <code>VEVENT</code> entry with <code>DTEND</code> and 
 * <code>DTSTART</code> components containing proper timezones. An 
 * {@link IcalException} will be thrown during construction if these conditions 
 * are not met.</p>
 *
 * <p>Most getters in this class return <code>String</code>s. Those methods 
 * return an empty <code>String</code> if no data could be retrieved.  Methods 
 * which return <code>Date</code> instances may return <code>null</code> if no 
 * data could be retrieved for them.</p>
 * 
 * @author aslag
 *
 */
class SingleEvent
{
  Calendar calendar

  /**
   * <p>Parse data from given {@link Calendar}.</p>
   * 
   * @throws IcalException In the case that an error occurs during validation.
   */
  public SingleEvent(Calendar calendar) 
    throws IcalException
  {
    this.calendar = calendar
    validate()
  }
  
  /**
   * @return <code>String</code>
   * 
   * @throws IcalException In the case that an error arises while retrieving
   * data.
   */
  String getSummary()
    throws IcalException
  {
    retrievePropertyString("SUMMARY")
  }

  /**
   * @return <code>String</code>
   * 
   * @throws IcalException In the case that an error arises while retrieving
   * data.
   */
  String getDescription()
    throws IcalException
  {
    retrievePropertyString("DESCRIPTION")
  }

  /**
   * @return <code>String</code>
   * 
   * @throws IcalException In the case that an error arises while retrieving
   * data.
   */
  String getLocation()
    throws IcalException
  {
    retrievePropertyString("LOCATION")
  }

  /**
   * @return <code>Date</code>
   *
   * @throws IcalException In the case that an error arises while retrieving
   * data.
   */
  Date getStart()
    throws IcalException 
  {
    retrieveDate("DTSTART")
  }

  /**
   * @return <code>Date</code>
   *
   * @throws IcalException In the case that an error arises while retrieving
   * data.
   */
  Date getEnd()
    throws IcalException
  {
    retrieveDate("DTEND")
  }

  /**
   * @return <code>String</code>
   */
  private String retrievePropertyString(String propertyName)
  {
    String propName = retrieveProperty(propertyName)?.value
    propName == null ? "" : propName
  }
  
  /**
   * @return <code>Date</code>
   *
   * @throws IcalException In the case that an error arises while retrieving
   * data.
   */
  private Date retrieveDate(String propertyName)
    throws IcalException
  {
    Property prop = retrieveProperty(propertyName)
    if (!(prop.date instanceof Date))
      throw new IcalException("Expected property retrieved for name " + propertyName + " to be of type Date, but it wasn't.")
    prop.date
  }
  
  /**
   * @return {@link Property}
   */
  private Property retrieveProperty(String propertyName)
  {
    calendar.getComponent("VEVENT").getProperty(propertyName)
  }

  /**
   * <p>Validates member {@link Calendar} object to ensure assumptions made 
   * about it (in simplified getters, for instance), are not misguided.</p>
   */
  private void validate()
  throws IcalException {
    // ensure this ical only contains one event
    if (calendar.getComponents("VEVENT").size() != 1)
      throw new IcalException("Calendar does not have single event as expected. Calendar evaluated: "+calendar)

    if (!retrieveProperty("DTSTART"))
      throw new IcalException("Calendar's VEVENT entry does not contain a proper DTSTART field: "+calendar)

    if (!retrieveProperty("DTEND"))
      throw new IcalException("Calendar's VEVENT entry does not contain a proper DTEND field: "+calendar)
  }
}
