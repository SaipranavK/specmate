"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var Url_1 = require('../../util/Url');
var router_1 = require('@angular/router');
var specmate_data_service_1 = require('../../services/specmate-data.service');
var core_1 = require('@angular/core');
var TestSpecificationEditor = (function () {
    function TestSpecificationEditor(dataService, router, route) {
        this.dataService = dataService;
        this.router = router;
        this.route = route;
    }
    TestSpecificationEditor.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.dataService.readElement(Url_1.Url.fromParams(params)); })
            .subscribe(function (testSpec) {
            _this.testSpecification = testSpec;
            _this.dataService.readContents(testSpec.url).then(function (contents) {
                _this.contents = contents;
            });
        });
    };
    TestSpecificationEditor = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'test-specification-editor',
            templateUrl: 'test-specification-editor.component.html'
        }), 
        __metadata('design:paramtypes', [specmate_data_service_1.SpecmateDataService, router_1.Router, router_1.ActivatedRoute])
    ], TestSpecificationEditor);
    return TestSpecificationEditor;
}());
exports.TestSpecificationEditor = TestSpecificationEditor;
//# sourceMappingURL=test-specification-editor.component.js.map