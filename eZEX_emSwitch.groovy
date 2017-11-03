/**
 *  eZEX C2O Outlet
 *
 *  Copyright 2017 Ezex Corp
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
    definition(name: "eZEX emSwitch", namespace: "eZEX", author: "eZEX Corp", ocfDeviceType: "oic.d.smartplug") {
        capability "Actuator"
        capability "Configuration"
        capability "Refresh"
        capability "Switch"
        capability "Outlet"
        capability "Power Meter"
        capability "Power Source"
        capability "Voltage Measurement"


        attribute "currentValue", "string"
        attribute "powerCutOff", "string"
        attribute "accumulate", "string"

        fingerprint profileId: "0104", deviceId: "0002", inClusters: "0000, 0003, 0004, 0006, 0B04, 0702", manufacturer: "eZEX Corp", model: "E210-KR116Z-HA"
        //outcluster제외
    }

    tiles(scale: 2) {
        multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
            tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "on", label:'${name}', action:"switch.off", icon:"http://www.ezex.co.kr/img/st/icon_main_electron_switch_on.png", backgroundColor:"#ffc000", nextState:"TurningOff"
                attributeState "off", label:'${name}', action:"switch.on", icon:"http://www.ezex.co.kr/img/st/icon_main_electron_switch_off.png", backgroundColor:"#ffffff", nextState:"TurningOn"
                attributeState "TurningOn", label:'Turning On', icon:"http://www.ezex.co.kr/img/st/icon_main_electron_switch_on.png", backgroundColor:"#c49300", nextState:"TurningOff"
                attributeState "TurningOff", label:'Turning Off', icon:"http://www.ezex.co.kr/img/st/icon_main_electron_switch_off.png", backgroundColor:"#8e6b00", nextState:"TurningOn"
            }

            tileAttribute ("power", key: "SECONDARY_CONTROL") {
                attributeState "power", label:'${currentValue} W', icon: "st.Appliances.appliances17"
            }

        }
        standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
        }
        valueTile("voltage", "device.voltage", width:4, height:2 ) {
            state "voltage", label: '전압\n${currentValue} V', defaultState: true
        }
        valueTile("current", "device.currentCurrent", width:2, height:2 ) {
            state "val", label: '전류\n${currentValue}A', defaultState: true
        }
        valueTile("accumulate", "device.accumulate", width:4, height:2 ) {
            state "val", label: '누적전력\n${currentValue}Wh', defaultState: true
        }


        main "switch"
        details(["switch", "voltage", "current", "accumulate", "refresh"])//details(["switch", "voltage", "current", "accumulate", "powerCutOff", "refresh"])
    }

}

def off() {
    zigbee.off()
}

def on() {
    zigbee.on()
}

private getSafeNumberValueFromState(stateKey) {
    def val = state[stateKey]
    def calVal = val ?: 0
    calVal = calVal instanceof Number ? calVal : zigbee.convertHexToInt(calVal)

    return calVal
}

// 현재 전압
private getVoltage(value) {
    def voltage = 0
    if( value ) {
        voltage = zigbee.convertHexToInt(value)
    }
    voltage = voltage / 1000
    return voltage
}

// 현재 전류
private getCurrent(value) {
    def current = 0
    if( value ) {
        current = zigbee.convertHexToInt(value)
    }
    current = current / 1000
    return current
}

// 누적전력
private getAccumlate(currentSummationDelivered) {
    def calCrrentSummationDelivered = currentSummationDelivered ?: 0
    calCrrentSummationDelivered = calCrrentSummationDelivered instanceof Number ? calCrrentSummationDelivered : zigbee.convertHexToInt(calCrrentSummationDelivered)
    def calMeteringMultiplier = getSafeNumberValueFromState('meteringMultiplier')
    def calMeteringDivisor = getSafeNumberValueFromState('meteringDivisor')
    if( calMeteringDivisor == 0 ) {
        calMeteringDivisor = 1
    }
    def accumulateWh = calCrrentSummationDelivered * calMeteringMultiplier / calMeteringDivisor * 1000

    def pattern = "##,###.###"
    def df = new java.text.DecimalFormat(pattern)
    def formatted = df.format(accumulateWh)
    return formatted
}

// 순시 전력
private getInstantDemend(instantDemend) {
    def calInstantDemend = instantDemend ?: 0
    calInstantDemend = calInstantDemend instanceof Number ? calInstantDemend : zigbee.convertHexToInt(calInstantDemend)
    def calMeteringMultiplier = getSafeNumberValueFromState('meteringMultiplier')
    def calMeteringDivisor = getSafeNumberValueFromState('meteringDivisor')
    if( calMeteringDivisor == 0 ) {
        calMeteringDivisor = 1
    }
    def momentaryW = calInstantDemend * calMeteringMultiplier / calMeteringDivisor * 1000
    return momentaryW
}

// Parse incoming device messages to generate events
def parse(String description) {

    log.info "LOG-MH:  parse()------------------------------------- $description"

    def parseMap = zigbee.parseDescriptionAsMap(description)
    log.info "LOG-MH: zigbee.parseDescriptionAsMap------------- $parseMap"
    def event = zigbee.getEvent(description)
    log.info "LOG-MH: zigbee.getEvent------------- $event"


    def clusterId
    def attrId
    def commandData
    if( parseMap != null ) {
        clusterId = parseMap.cluster ? parseMap.cluster : parseMap.clusterId
        log.info "LOG-MH: clusterId--1----------- $clusterId"
        clusterId = (clusterId != null) ? clusterId.toUpperCase() : null
        log.info "LOG-MH: clusterId--2----------- $clusterId"
        attrId = parseMap.attrId != null ? parseMap.attrId.toUpperCase() : null
        log.info "LOG-MH: attrId-------------- $attrId"
        log.info "LOG-MH:  parseMap.data-------------- $parseMap.data"
        if( parseMap.data != null ) {
            commandData = String.valueOf(parseMap.data[0])
            log.info "LOG-MH: commandData-------------- $commandData"
        }
    }

    log.info "LOG-MH: event.name------------- $event.name"
    if( event != null && event.name == "switch") {
        sendEvent(event)
    }

    def eventStack = []
    if( parseMap != null ) {
        def forceReturn = false
        if( clusterId == "0702" ) {

            def attrProcessor = { theAttrId, value ->
                if( theAttrId == "0301") {
                    state.meteringMultiplier = value
                    forceReturn = true
                } else if( theAttrId == "0302") {
                    state.meteringDivisor = value
                    forceReturn = true
                } else if( theAttrId == "0000" ) {
                    def accumulateWh = getAccumlate(value)
                    eventStack.push(createEvent(name: "accumulate", value: accumulateWh))
                } else if( theAttrId == "0400") {
                    def instantDemend = getInstantDemend(value)
                    eventStack.push(createEvent(name: "power", value: instantDemend))
                } else if(theAttrId == "0901") {
                    def current = getCurrent(value)
                    eventStack.push(createEvent( name: "currentCurrent", value: current))
                } else if(theAttrId == "0902") {
                    def voltage = getVoltage(value)
                    eventStack.push(createEvent( name: "voltage", value: voltage))
                } else if(theAttrId == "0905" ) {
                    log.debug "${clusterId}, ${theAttrId}, ${value}, description is ${description}"
                    def powerCutOffEnabled = value == "01" ? "enabled" : "disabled"
                    eventStack.push(createEvent( name: "powerCutOff", value: powerCutOffEnabled))
                } else {
                    log.warn "Unhandle cluster: ${clusterId}, ${theAttrId}, ${value}, description is ${description}"
                }

            };

            def attrs = parseMap.additionalAttrs // 이젝스 확장
            if( attrs == null ) {   // 개별 리포팅: Refresh 호출시 개별 attribute는 이 속성으로 읽는다

                attrProcessor(attrId, parseMap.value)
            } else { // 자동 리포팅: 디바이스에서 리포팅하는 정보는 attrs 배열 형태이다.

                attrs.each { attr ->
                    attrProcessor(attr.attrId, attr.value)
                }
            }
        }
        // log.debug "eventStack: ${eventStack}, ${clusterId}, ${attrId}"
        if(!eventStack.isEmpty()) {
            return eventStack
        }
        if( forceReturn ) {
            return
        }
    }
}


def ping() {
    device.endpointId = 1
    return zigbee.onOffRefresh()
}

def refresh() {
    def endpointId = 1
    def delay = 50
    def cmds = []

    // on/off
    cmds << "st rattr 0x${device.deviceNetworkId} ${endpointId} 0x0006 0x0000"
    cmds << "delay ${delay}"

    // multiplier & divisor
    cmds << "st rattr 0x${device.deviceNetworkId} ${endpointId} 0x0702 0x0301"
    cmds << "delay ${delay}"
    cmds << "st rattr 0x${device.deviceNetworkId} ${endpointId} 0x0702 0x0302"
    cmds << "delay ${delay}"

    // 누적전력
    cmds << "st rattr 0x${device.deviceNetworkId} ${endpointId} 0x0702 0x0000"
    cmds << "delay ${delay}"

    // 순시전력
    cmds << "st rattr 0x${device.deviceNetworkId} ${endpointId} 0x0702 0x0400"
    cmds << "delay ${delay}"

    // 전류
    cmds << "st rattr 0x${device.deviceNetworkId} ${endpointId} 0x0702 0x0901"
    cmds << "delay ${delay}"

    // 전압
    cmds << "st rattr 0x${device.deviceNetworkId} ${endpointId} 0x0702 0x0902"
    cmds << "delay ${delay}"

    log.info "refresh operaion requested"
    return cmds
}
