package com.firebase4s.database

import com.google.firebase.database
import scala.collection.JavaConverters._
import com.firebase4s.util.FirebaseData._
import scala.reflect._
import DataConversions._

case class DataSnapshot(private val snapshot: database.DataSnapshot) {

  /**
    * Get a snapshot at the specified path
    * @param path
    * @return
    */
  def child(path: String): DataSnapshot = DataSnapshot(snapshot.child(path))

  /**
    * Returns true if the snapshot contains a (non-null) value
    * @return
    */
  def exists: Boolean = snapshot.exists()

  /**
    * Get all of the immediate children of the snapshot
    * @return
    */
  def getChildren: Stream[DataSnapshot] =
    snapshot.getChildren.asScala.toStream.map(DataSnapshot)

  /**
    * Get the number of immediate children of this snapshot
    * @return
    */
  def getChildrenCount: Long = snapshot.getChildrenCount

  /**
    * Get the data contained within the snapshot as an Option
    * @return
    */
  def getValue: Option[Any] =
    if (snapshot.exists()) {
      Some(DataConversions.snapshotValueAsScala(snapshot.getValue))
    } else {
      None
    }

  /**
    * Return the underlying value of the snapshot if it exists. Otherwise return
    * the provided default value.
    * @return
    */
  def getValueOrElse(a: Any): Any =
    if (snapshot.exists()) {
      DataConversions.snapshotValueAsScala(snapshot.getValue)
    } else {
      a
    }

  /**
    * Get the data contained within the snapshot as an Option[A],
    * where the class of type A is provided as a parameter
    * @tparam A
    * @return
    */
  def getValue[A](valueType: Class[A]): Option[A] =
    if (snapshot.exists()) {
      Some(snapshot.getValue(valueType))
    } else {
      None
    }

  /**
    * Get the data contained within the snapshot as an Option[A],
    * where the class of type A is provided as a parameter
    * @return
    */
  def getDataMap: Option[Map[String, Any]] =
    if (snapshot.exists()) {
      val m = Option(DataConversions.snapshotValueAsScala(snapshot.getValue).asInstanceOf[Map[String, Any]])
      m
    } else {
      None
    }

  /**
    * Get the data contained within the snapshot as an Option[A],
    * where the class of type A is provided as a parameter
    * @return
    */
  def getValueAs[A]: Option[A] =
    if (snapshot.exists()) {
      val m = Option(DataConversions.snapshotValueAsScala(snapshot.getValue).asInstanceOf[A])
      m
    } else {
      None
    }

  /**
    * Get the data contained within the snapshot as an Option[A],
    * where the class of type A is provided as a parameter
    * @return
    */
  def getValueAsDataMap[A]: Option[Map[String, Any]] =
    if (snapshot.exists()) {
      Option(DataConversions.snapshotValueAsScala(snapshot.getValue).asInstanceOf[Map[String, Any]])
    } else {
      None
    }

  /**
    * Does the snapshot have data at a particular path?
    * @param path
    * @return
    */
  def hasChild(path: String): Boolean = snapshot.hasChild(path)

  /**
    * Does the snapshot have any children?
    * @return
    */
  def hasChildren: Boolean = snapshot.hasChildren
}
