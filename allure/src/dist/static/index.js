var MyTabModel = Backbone.Collection.extend({
    url: 'data/plugindata.json'
})

class MyLayout extends allure.components.AppLayout {

    initialize() {
        this.model = new MyTabModel();
    }

    loadData() {
        return this.model.fetch();
    }
    
    getContentView() {
        return new MyView({items: this.model.models});
    }
}

const template = function (data) {
    html = '<h3 class="pane__title">... Data from the file ...</h3> ' + data.length;
    
    for (var item of data.items) {
        html += '<p>' + item.attributes.name + ' says: ' + item.attributes.sounds + '</p>';
    }
    return html;
}

var MyView = Backbone.Marionette.View.extend({
    template: template,

    render: function () {
        this.$el.html(this.template(this.options));
        return this;
    }
})

allure.api.addTab('mytab', {
    title: 'Custom Tab', icon: 'fa fa-trophy',
    route: 'mytab',
    onEnter: (function () {
        return new MyLayout()
    })
});