/**
 *  eZEX Switch
 *
 *  Copyright 2017 eZEX Corp
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
    definition(name: "eZEX Switch 5", namespace: "eZEX", author: "eZEX Corp", ocfDeviceType: "oic.d.switch") {
        capability "Actuator"
        capability "Configuration"
        capability "Refresh"
        capability "Switch"
        capability "Polling"
        capability "Light"
        capability "Sensor"

        attribute "switch1", "string"
        attribute "switch2", "string"
        attribute "switch3", "string"
        attribute "switch4", "string"
        attribute "switch5", "string"
        attribute "switch6", "string"
        attribute "switchAll", "string"
        attribute "on", "string"
        attribute "off", "string"

        command "on1"
        command "off1"
        command "on2"
        command "off2"
        command "on3"
        command "off3"
        command "on4"
        command "off4"
        command "on5"
        command "off5"
        command "on6"
        command "off6"
        command "on"
        command "off"

        fingerprint profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006", manufacturer: "eZEX Corp", model: "E220-KR5N0Z0-HA", deviceJoinName: "eZEX Switch 5"
    }

    // simulator metadata
    simulator {
        // status messages
        status "on": "on/off: 1"
        status "off": "on/off: 0"

        // reply messages
        reply "zcl on-off on": "on/off: 1"
        reply "zcl on-off off": "on/off: 0"
    }

    tiles(scale: 2) {
        multiAttributeTile(name: "switch", type: "lighting", decoration: "flat", width: 6, height: 4, defaultState: true) {
            tileAttribute("device.switchAll", key: "PRIMARY_CONTROL", decoration: "flat") {
                attributeState "on", label: 'ON', action: "off", icon: "http://www.ezex.co.kr/img/st/icon_main_light_switch_on.png", backgroundColor: "#ffc000", nextState: "turningOff"
                attributeState "off", label: 'OFF', action: "on", icon: "http://www.ezex.co.kr/img/st/icon_main_light_switch_off.png", backgroundColor: "#ffffff", nextState: "turningOn"
                attributeState "turningOn", label: 'Turning On..', icon: "http://www.ezex.co.kr/img/st/icon_main_light_switch_on.png", backgroundColor: "#c49300", nextState: "turningOff"
                attributeState "turningOff", label: 'Turning Off..', icon: "http://www.ezex.co.kr/img/st/icon_main_light_switch_off.png", backgroundColor: "#8e6b00", nextState: "turningOn"
            }
        }

        standardTile("switch1", "device.switch1", decoration: "flat", width: 1, height: 1) {
            state "on", label: "", action: "off1", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#ffffff", nextState: "turningOff"
            state "off", label: "", action: "on1", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#ffffff", nextState: "turningOn"
            state "turningOn", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#e5e5e5", nextState: "turningOff"
            state "turningOff", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#e5e5e5", nextState: "turningOn"
        }
        standardTile("switch2", "device.switch2", decoration: "flat", width: 1, height: 1) {
            state "on", label: "", action: "off2", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#ffffff", nextState: "turningOff"
            state "off", label: "", action: "on2", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#ffffff", nextState: "turningOn"
            state "turningOn", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#e5e5e5", nextState: "turningOff"
            state "turningOff", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#e5e5e5", nextState: "turningOn"
        }
        standardTile("switch3", "device.switch3", decoration: "flat", width: 1, height: 1) {
            state "on", label: "", action: "off3", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#ffffff", nextState: "turningOff"
            state "off", label: "", action: "on3", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#ffffff", nextState: "turningOn"
            state "turningOn", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#e5e5e5", nextState: "turningOff"
            state "turningOff", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#e5e5e5", nextState: "turningOn"
        }
        standardTile("switch4", "device.switch4", decoration: "flat", width: 1, height: 1) {
            state "on", label: "", action: "off4", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#ffffff", nextState: "turningOff"
            state "off", label: "", action: "on4", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#ffffff", nextState: "turningOn"
            state "turningOn", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#e5e5e5", nextState: "turningOff"
            state "turningOff", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#e5e5e5", nextState: "turningOn"
        }
        standardTile("switch5", "device.switch5", decoration: "flat", width: 1, height: 1) {
            state "on", label: "", action: "off5", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#ffffff", nextState: "turningOff"
            state "off", label: "", action: "on5", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#ffffff", nextState: "turningOn"
            state "turningOn", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#e5e5e5", nextState: "turningOff"
            state "turningOff", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#e5e5e5", nextState: "turningOn"
        }
        standardTile("switch6", "device.switch6", decoration: "flat", width: 1, height: 1) {
            state "on", label: "", action: "off6", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#ffffff", nextState: "turningOff"
            state "off", label: "", action: "on6", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#ffffff", nextState: "turningOn"
            state "turningOn", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#e5e5e5", nextState: "turningOff"
            state "turningOff", label: '', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#e5e5e5", nextState: "turningOn"
        }
        standardTile("on", "device.on", decoration: "flat", width: 2, height: 2) {
            state "default", label: 'ON ALL', action: "on", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#ffffff", nextState: "turningOn"
            state "turningOn", label: 'ON ALL', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on.png", backgroundColor: "#e5e5e5", nextState: "default"
            state "disabled", label: 'ON ALL', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_on_30.png", backgroundColor: "#ffffff", nextState: "default", textColor: ""
        }
        standardTile("off", "device.off", decoration: "flat", width: 2, height: 2) {
            state "default", label: 'OFF ALL', action: "off", icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#ffffff", nextState: "turningOff"
            state "turningOff", label: 'OFF ALL', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off.png", backgroundColor: "#e5e5e5", nextState: "default"
            state "disabled", label: 'OFF ALL', icon: "http://www.ezex.co.kr/img/st/btn_remote_power_off_30.png", backgroundColor: "#ffffff", nextState: "default", textColor: "#ff0000"
        }
        standardTile("refresh", "device.refresh", inactiveLabel: true, decoration: "flat", width: 4, height: 1) {
            state "default", label: "", action: "refresh.refresh", icon: "st.secondary.refresh"
        }
        standardTile("space", "device.space", decoration: "flat", width: 1, height: 1) {

        }

        main "switch"
        details(["switch", "switch4", "switch1", "on", "off", "space", "switch2", "switch5", "switch3", "refresh"])
    }
}

// Parse incoming device messages to generate events
def parse(String description) {
    log.debug "description is $description"

    if (description?.startsWith("on/off:")) {
//        def cmd = zigbee.readAttribute(0x0006, 0x0011)
        def cmd = ["st rattr 0x${device.deviceNetworkId} 0x01 0x0006 0x0011"]
        log.debug "read attribute request: ${cmd}"
        return cmd?.collect { new physicalgraph.device.HubAction(it) }
    } else {
        def descMap = zigbee.parseDescriptionAsMap(description)
        if (descMap.clusterId && descMap.sourceEndpoint && descMap.command) {
            log.debug "---------------------------------"
            log.debug "$descMap"
            log.debug "---------------------------------"

            if (descMap.clusterId == "0006" && descMap.command == "01" && descMap.attrId == "0011") {
                String value = hexToBin(descMap.data[4])
                log.debug "read attributes response for on/off cluster (endpoint=$descMap.sourceEndpoint, status=${descMap.data[2]}, type=${descMap.data[3]}, value=${value})"
                char[] values = value.toCharArray()

                state.sw1 = values[7] == "1" ? "on" : "off"
                sendEvent(name: "switch1", value: state.sw1)
                log.debug "switch1: ${state.sw1}"

                state.sw2 = values[6] == "1" ? "on" : "off"
                sendEvent(name: "switch2", value: state.sw2)
                log.debug "switch2: ${state.sw2}"

                state.sw3 = values[5] == "1" ? "on" : "off"
                sendEvent(name: "switch3", value: state.sw3)
                log.debug "switch3: ${state.sw3}"

                state.sw4 = values[4] == "1" ? "on" : "off"
                sendEvent(name: "switch4", value: state.sw4)
                log.debug "switch4: ${state.sw4}"

                state.sw5 = values[3] == "1" ? "on" : "off"
                sendEvent(name: "switch5", value: state.sw5)
                log.debug "switch5: ${state.sw5}"

                state.sw6 = values[2] == "1" ? "on" : "off"
                sendEvent(name: "switch6", value: state.sw6)

                checkStatus()

            } else if (descMap.clusterId == "0006" && descMap.command == "0B" && descMap.data[1] == "00") {
                log.debug "default response for on/off cluster (command=$descMap.command, endpoint=$descMap.sourceEndpoint, value=${descMap.data[0]})"
                def value = descMap.data[0] == "01" ? "turningOn" : "turningOff"
                if (descMap.sourceEndpoint == "01") {
                    state.sw1 = value
                    sendEvent(name: "switch1", value: state.sw1)
                    log.debug "switch1: ${state.sw1}"
                } else if (descMap.sourceEndpoint == "02") {
                    state.sw2 = value
                    sendEvent(name: "switch2", value: state.sw2)
                    log.debug "switch2: ${state.sw2}"
                } else if (descMap.sourceEndpoint == "03") {
                    state.sw3 = value
                    sendEvent(name: "switch3", value: state.sw3)
                    log.debug "switch3: ${state.sw3}"
                } else if (descMap.sourceEndpoint == "04") {
                    state.sw4 = value
                    sendEvent(name: "switch4", value: state.sw4)
                    log.debug "switch4: ${state.sw4}"
                } else if (descMap.sourceEndpoint == "05") {
                    state.sw5 = value
                    sendEvent(name: "switch5", value: state.sw5)
                    log.debug "switch5: ${state.sw5}"
                } else if (descMap.sourceEndpoint == "06") {
                    state.sw6 = value
                    sendEvent(name: "switch6", value: state.sw6)
                    log.debug "switch6: ${state.sw6}"
                }
            } else {
                log.warn "Unknown Response!!!"
            }
        } else {
            log.warn "descMap is NOT correct - ${descMap}"
        }
    }
}

def String hexToBin(String hex) {
    int len = hex.length() * 4;
    String bin = new BigInteger(hex, 16).toString(2);

    if (bin.length() < len) {
        int diff = len - bin.length();
        String pad = "";
        for (int i = 0; i < diff; ++i) {
            pad = pad.concat("0");
        }
        bin = pad.concat(bin);
    }
    return bin;
}

def on1() {
//    zigbee.command(0x0006, 0x01, "", [destEndpoint: 1])
    return ["st cmd 0x${device.deviceNetworkId} 0x01 0x0006 0x01 {}"]
}

def off1() {
//    zigbee.command(0x0006, 0x00, "", [destEndpoint: 1])
    return ["st cmd 0x${device.deviceNetworkId} 0x01 0x0006 0x00 {}"]
}

def on2() {
//    zigbee.command(0x0006, 0x01, "", [destEndpoint: 2])
    return ["st cmd 0x${device.deviceNetworkId} 0x02 0x0006 0x01 {}"]
}

def off2() {
//    zigbee.command(0x0006, 0x00, "", [destEndpoint: 2])
    return ["st cmd 0x${device.deviceNetworkId} 0x02 0x0006 0x00 {}"]
}

def on3() {
//    zigbee.command(0x0006, 0x01, "", [destEndpoint: 3])
    return ["st cmd 0x${device.deviceNetworkId} 0x03 0x0006 0x01 {}"]
}

def off3() {
//    zigbee.command(0x0006, 0x00, "", [destEndpoint: 3])
    return ["st cmd 0x${device.deviceNetworkId} 0x03 0x0006 0x00 {}"]
}

def on4() {
//    zigbee.command(0x0006, 0x01, "", [destEndpoint: 4])
    return ["st cmd 0x${device.deviceNetworkId} 0x04 0x0006 0x01 {}"]
}

def off4() {
//    zigbee.command(0x0006, 0x00, "", [destEndpoint: 4])
    return ["st cmd 0x${device.deviceNetworkId} 0x04 0x0006 0x00 {}"]
}

def on5() {
//    zigbee.command(0x0006, 0x01, "", [destEndpoint: 5])
    return ["st cmd 0x${device.deviceNetworkId} 0x05 0x0006 0x01 {}"]
}

def off5() {
//    zigbee.command(0x0006, 0x00, "", [destEndpoint: 5])
    return ["st cmd 0x${device.deviceNetworkId} 0x05 0x0006 0x00 {}"]
}

def on6() {
//    zigbee.command(0x0006, 0x01, "", [destEndpoint: 6])
    return ["st cmd 0x${device.deviceNetworkId} 0x06 0x0006 0x01 {}"]
}

def off6() {
//    zigbee.command(0x0006, 0x00, "", [destEndpoint: 6])
    return ["st cmd 0x${device.deviceNetworkId} 0x06 0x0006 0x00 {}"]
}

def on() {
    state.swAll = "turningOn"
    state.swOn = "turningOn"
    sendEvent(name: "switchAll", value: state.swAll)
    sendEvent(name: "on", value: state.swOn)
//    zigbee.command(0x0006, 0x01, "", [destEndpoint: 0xFF])
    return ["st cmd 0x${device.deviceNetworkId} 0xFF 0x0006 0x01 {}"]
}

def off() {
    state.swAll = "turningOff"
    state.swOff = "turningOff"
    sendEvent(name: "switchAll", value: state.swAll)
    sendEvent(name: "off", value: state.swOff)
//    zigbee.command(0x0006, 0x00, "", [destEndpoint: 0xFF])
    return ["st cmd 0x${device.deviceNetworkId} 0xFF 0x0006 0x00 {}"]
}

def checkStatus() {
    if (state.sw1 == "off" && state.sw2 == "off" && state.sw3 == "off" && state.sw4 == "off" && state.sw5 == "off") {
        state.swAll = "off"
        state.swOn = "default"
        state.swOff = "disabled"
    } else {
        state.swAll = "on"
        if (state.sw1 == "on" && state.sw2 == "on" && state.sw3 == "on" && state.sw4 == "on" && state.sw5 == "on") {
            state.swOn = "disabled"
        } else {
            state.swOn = "default"
        }
        state.swOff = "default"
    }
    sendEvent(name: "switchAll", value: state.swAll)
    sendEvent(name: "on", value: state.swOn)
    sendEvent(name: "off", value: state.swOff)
    log.debug "switchAll: ${state.swAll}"
    log.debug "on: ${state.swOn}"
    log.debug "off: ${state.swOff}"
}

/**
 * PING is used by Device-Watch in attempt to reach the Device
 * */
def ping() {
    return refresh()
}

def refresh() {
//    zigbee.readAttribute(0x0006, 0x0011)
    return ["st rattr 0x${device.deviceNetworkId} 0x01 0x0006 0x0011"]
}

def configure() {
    // Device-Watch allows 2 check-in misses from device + ping (plus 2 min lag time)
    state.updatedTime = Calendar.getInstance().getTimeInMillis()
    sendEvent(name: "checkInterval", value: 2 * 10 * 60 + 2 * 60, displayed: false, data: [protocol: "zigbee", hubHardwareId: device.hub.hardwareID])
    log.debug "Configuring Reporting and Bindings."

//    zigbee.readAttribute(0x0006, 0x0011)
    return ["st rattr 0x${device.deviceNetworkId} 0x01 0x0006 0x0011"]
}
