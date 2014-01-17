'use strict';
angular.module('heartbeat.home', [
    'ui.router.state',
]).config(function config($stateProvider) {
    $stateProvider.state('home', {
        url: '/home',
        views: {
            "main": {
                controller: "HomeCtrl",
                templateUrl: 'home/home.tpl.html',
                data:{ pageTitle: 'Home' }
            }
        }
    })
}).controller('HomeCtrl', function HomeController($scope) {
    

})
