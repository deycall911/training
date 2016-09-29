var app = angular.module('filterSpecial', ['ngMaterial', 'ngMessages', 'material.svgAssetsCache']);

app.directive('fileModel', ['$parse', function($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}]);

app.service('fileUpload', ['$http', function($http) {
	this.uploadFileToUrl = function(file, name, tag, uploadUrl) {
		var fd = new FormData();
		fd.append('file', file);
		fd.append('name', name);
		fd.append('tag', tag);

		$http.put(uploadUrl, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		}).success(function() {

		}).error(function(e) {
			console.log(e);
		});
	}
}]);

app.controller('filterCtrl', ['$scope', 'fileUpload', '$mdDialog', '$http', function($scope, fileUpload, $mdDialog, $http) {
	$scope.startsWith = function(actual, expected) {
		var lowerStr = (actual + "").toLowerCase();
		return lowerStr.indexOf(expected.toLowerCase()) === 0;
	}

	$scope.getTagsForImageWithId = function() {
		for (i = 0; i < $scope.images.length; i++) {
			$scope.getTags(i);
		}
	}

	$scope.getTags = function(i) {
		$http.get('/tags/image/' + $scope.images[i].id).success(function(data) {
			$scope.images[i].tags = data;
		}).error(function(e) {
			console.log(e);
		});
	}

	$scope.getAllImages = function() {
		$http.get('/images/').success(function(data) {
			$scope.images = data;
			$scope.getTagsForImageWithId();
		});
	}

	$scope.uploadFile = function() {
		var uploadUrl = "/image";

		console.log(uploadUrl);
		var file = $scope.myFile;
		console.log('file is ');
		console.dir(file);
		fileUpload.uploadFileToUrl(file, $scope.name, $scope.tag, uploadUrl);
	};

	$scope.showAdvanced = function(ev, id, name, tags) {
		$mdDialog.show({
				controller: DialogController,
				template: '<img src="/image/' + id + '/png" ></img> <p style="margin:3px">Name: ' + name + '</p><p style="margin:5px">Tags: ' + tags.toString() + '</p>',
				parent: angular.element(document.body),
				targetEvent: ev,
				clickOutsideToClose: true,
			})
			.then(function(answer) {
				$scope.status = 'You said the information was "' + answer + '".';
			}, function() {
				$scope.status = 'You cancelled the dialog.';
			});
	};

	function DialogController($scope, $mdDialog) {
		$scope.hide = function() {
			$mdDialog.hide();
		};

		$scope.cancel = function() {
			$mdDialog.cancel();
		};

		$scope.answer = function(answer) {
			$mdDialog.hide(answer);
		};
	}
}]);