KISSY.use("dom,event,imagezoom", function(S, DOM, Event, ImageZoom) {
    var m = new ImageZoom({
        imageNode: "#multi",
        align:{
            node: "#multi",
            points: ["tr","tl"],
            offset: [0, 0]
        }
    });

    Event.on("#imgList img", 'click', function() {
        var data = DOM.attr(this, 'data-ks-imagezoom');
        DOM.attr('#multi', 'src', data);
        m.set('hasZoom', DOM.attr(this, 'data-has-zoom'));
        if (data) {
            m.set('bigImageSrc', data);
        }
    });
});