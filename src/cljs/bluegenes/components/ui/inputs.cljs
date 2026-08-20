(ns bluegenes.components.ui.inputs
  (:require [reagent.core :as reagent]
            [oops.core :refer [oget]]))

(defn password-input []
  (let [show-password? (reagent/atom false)]
    (fn [{:keys [value on-change on-submit container-class new-password? label disabled]}]
      [:div.form-group.toggle-show-password
       {:class container-class}
       [:label (or label "Password")]
       ;; The icon is positioned relative to this wrapper (not the whole
       ;; form-group, which also contains the label) so it stays vertically
       ;; centred on the input itself regardless of label height/margins.
       [:div.password-field
        [:input.form-control
         (merge {:type (if @show-password? "text" "password")
                 :value value
                 :on-change on-change
                 :on-key-up #(when (= 13 (oget % :keyCode))
                               (on-submit))
                 :disabled disabled}
                (when new-password?
                  {:autoComplete "new-password"}))]
        (let [icon (str "icon-eye" (when @show-password? "-blocked"))]
          [:a {:role "button"
               :on-click #(swap! show-password? not)
               :title (str (if @show-password? "Hide" "Show") " password")}
           [:svg.icon {:class icon}
            [:use {:xlinkHref (str "#" icon)}]]])]])))
