import {environment} from "../../../environments";

export const NotificationConfig = {
    api: environment.apis.notification+'/api/notifications/',
    ws: environment.apis.notification+'/ws/'
};