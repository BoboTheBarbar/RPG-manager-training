/* tslint:disable */
/* eslint-disable */
/**
 * Vendor Management API
 * API for managing vendor items
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface ModelError
 */
export interface ModelError {
    /**
     * 
     * @type {string}
     * @memberof ModelError
     */
    message?: string;
    /**
     * 
     * @type {string}
     * @memberof ModelError
     */
    code?: string;
}

/**
 * Check if a given object implements the ModelError interface.
 */
export function instanceOfModelError(value: object): value is ModelError {
    return true;
}

export function ModelErrorFromJSON(json: any): ModelError {
    return ModelErrorFromJSONTyped(json, false);
}

export function ModelErrorFromJSONTyped(json: any, ignoreDiscriminator: boolean): ModelError {
    if (json == null) {
        return json;
    }
    return {
        
        'message': json['message'] == null ? undefined : json['message'],
        'code': json['code'] == null ? undefined : json['code'],
    };
}

export function ModelErrorToJSON(json: any): ModelError {
    return ModelErrorToJSONTyped(json, false);
}

export function ModelErrorToJSONTyped(value?: ModelError | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'message': value['message'],
        'code': value['code'],
    };
}

