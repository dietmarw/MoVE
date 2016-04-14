/**
 * Copyright (C) 2016 Nicola Justus <nicola.justus@mni.thm.de>
 */


package de.thm.move.util

import de.thm.move.controllers.implicits.FxHandlerImplicits._
import javafx.scene.Node
import javafx.scene.control.ChoiceBox
import de.thm.move.views.Anchor
import de.thm.move.views.shapes.{ResizableShape, MovableShape}

/** General utils for working with JavaFx. */
object JFxUtils {
  /** Adds the given listener to the selectionProperty of the given ChoiceBox.
   * The eventHandler only gets the new value and discards the old value.
   */
  def onChoiceboxChanged[A](box:ChoiceBox[A])(eventHandler: A => Unit): Unit = {
    box.getSelectionModel.
      selectedItemProperty.addListener { (_:A, newA:A) =>
        eventHandler(newA)
      }
  }

  def binAnchorsLayoutToNodeLayout(node:Node)(anchors:Anchor*): Unit = {
    anchors.foreach { anchor =>
      anchor.layoutXProperty().bind(node.layoutXProperty())
      anchor.layoutYProperty().bind(node.layoutYProperty())
    }
  }

  def withMovableElement[A](n:Node)(fn: MovableShape => A):A = (n, n.getParent) match {
    case (_,ms:MovableShape) => fn(ms)
    case (ms:MovableShape,_) => fn(ms)
    case _ => throw new IllegalArgumentException(s"that's not a movableShape: $n")
  }

  def withResizableElement[A](n:Node)(fn: ResizableShape => A):A = (n, n.getParent) match {
    case (_,ms:ResizableShape) => fn(ms)
    case (ms:ResizableShape,_) => fn(ms)
    case _ => throw new IllegalArgumentException(s"that's not a resizableShape: $n")
  }
}
