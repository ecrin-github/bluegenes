(ns bluegenes.components.navbar.events
  (:require [re-frame.core :as re-frame :refer [reg-event-db reg-event-fx reg-fx reg-sub dispatch subscribe]]
            [oops.core :refer [ocall oapply oget oset!]]))

(reg-fx
 :visual-navbar-minechange
 (fn []
    ;;makes sure that the user notices the mine has changed.
   (let [navbar (.querySelector js/document ".minename")
         navbar-class (.-className navbar)]
     (oset! navbar ["className"] (str navbar-class " recently-changed"))
     (.setTimeout js/window #(oset! navbar ["className"] navbar-class)

                  3000))))

;; Whether the collapsible nav-links panel is open, on screens narrow enough
;; to show the hamburger toggle instead of the full nav inline.
(reg-event-db
 :nav/toggle-mobile-menu
 (fn [db [_]]
   (update db :mobile-menu-open? not)))

(reg-event-db
 :nav/close-mobile-menu
 (fn [db [_]]
   (assoc db :mobile-menu-open? false)))

(reg-sub
 :nav/mobile-menu-open?
 (fn [db]
   (:mobile-menu-open? db)))

