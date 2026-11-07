(function (window, $) {
    'use strict';
    var active = false;
    window.practiceProgress = {
        show: function (message) {
            active = true;
            $('#practice-progress-message').text(message);
            $('#practice-progress').show();
            $('#practice-submit').attr('aria-disabled', 'true');
        },
        hide: function () {
            active = false;
            $('#practice-progress').hide();
            $('#practice-submit').attr('aria-disabled', 'false');
        },
        start: function (uploader) {
            if (active) return false;
            if (!uploader.files.length) {
                $('#practiceNotification').attr('class', 'error').text('Choose files before submitting.');
                return false;
            }
            $('#practiceNotification').empty();
            this.show('Uploading your files. Please wait…');
            return true;
        },
        fail: function (message) {
            this.hide();
            $('#practiceNotification').attr('class', 'error').text(message);
        }
    };
})(window, jQuery);
