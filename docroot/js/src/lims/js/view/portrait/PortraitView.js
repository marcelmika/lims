/**
 * Portrait
 *
 * Represent buddy's portrait
 */
Y.namespace('LIMS.View');

Y.LIMS.View.PortraitView = Y.Base.create('portraitView', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<img alt="" class="portrait"/>',

    render: function () {
        // Vars
        var container = this.get("container"),
            settings = new Y.LIMS.Core.Settings();

        // Set portrait image src attribute based on the screen name
        container.set('src', settings.getPortraitUrl(this.get("screenName")));

        return this;
    }
}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.
        container: {
            valueFn: function () {
                return Y.Node.create(this.containerTemplate);
            }
        },

        screenName: {
            value: null
        }
    }
});
