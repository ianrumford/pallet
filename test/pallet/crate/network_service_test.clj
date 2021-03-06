(ns pallet.crate.network-service-test
  (:use
   clojure.test
   [pallet.common.logging.logutils :only [logging-threshold-fixture]])
  (:require
   [pallet.build-actions :as build-actions]
   [pallet.crate.network-service :as network-service]))

(use-fixtures :once (logging-threshold-fixture))

(deftest wait-for-port-listen-test
  (is
   (first
    (build-actions/build-actions
     {}
     (network-service/wait-for-port-listen 80)))))

(deftest wait-for-http-status-test
  (is
   (first
    (build-actions/build-actions
     {}
     (network-service/wait-for-http-status "http://localhost/" 200))))
  (re-find
   #"-b 'x=y'"
   (first
    (build-actions/build-actions
     {}
     (network-service/wait-for-http-status
      "http://localhost/" 200 :cookie "x=y"))))
  (re-find
   #"--header 'Cookie: x=y'"
   (first
    (build-actions/build-actions
     {}
     (network-service/wait-for-http-status
      "http://localhost/" 200 :cookie "x=y")))))
