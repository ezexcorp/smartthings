/**
 *  eZEX C2O Sub-Meter
 *
 *  Copyright 
 *  github: Euiho Lee (flutia)
 *  email: flutia@naver.com
 *  Date: 2017-09-11
 *  Copyright flutia and stsmarthome (cafe.naver.com/stsmarthome/) and eZEX Corp.
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
    definition(name: "eZEX Sub-Meter", namespace: "eZEX", author: "eZEX Corp", ocfDeviceType: "oic.d.smartplug") {
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

        fingerprint profileId: "0104", deviceId: "0002", inClusters: "0000, 0003, 0004, 0006, 0B04, 0702", manufacturer: "eZEX Corp", model: "E240-KR080Z-HA"
        //outcluster제외
    }

    tiles(scale: 2) {
        multiAttributeTile(name: "switch", type: "lighting", width: 6, height: 4, canChangeIcon: true) {
            tileAttribute("power", key: "PRIMARY_CONTROL") {
                attributeState "default", label: '${currentValue} W', icon: "http://www.ezex.co.kr/img/st/icon_main_meter_on.png", backgroundColor: "#ffc000"
            }

            tileAttribute("power", key: "SECONDARY_CONTROL") {
                attributeState "power", label: '${currentValue} W', icon: "st.Appliances.appliances17"
            }

        }
        standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "default", label: "", action: "refresh.refresh", icon: "st.secondary.refresh"
        }
        valueTile("voltage", "device.voltage", width: 4, height: 2) {
            state "voltage", label: '전압\n${currentValue} V', defaultState: true
        }
        valueTile("current", "device.currentCurrent", width: 2, height: 2) {
            state "val", label: '전류\n${currentValue} A', defaultState: true
        }
        valueTile("accumulate", "device.accumulate", width: 4, height: 2) {
            state "val", label: '누적전력\n${currentValue} Wh', defaultState: true
        }

        standardTile("prev", "device.prev", decoration: "flat", width: 2, height: 1) {
            state "default", label: "", action: "prev", icon: "st.Appliances.appliances17"
            state "disabled", label: "", action: "prev", icon: "st.Appliances.appliances7"
        }

        standardTile("next", "device.next", decoration: "flat", width: 2, height: 1) {
            state "default", label: "", action: "next", icon: "st.Appliances.appliances17"
            state "disabled", label: "", action: "prev", icon: "st.Appliances.appliances7"
        }
        valueTile("space1", "device.space", decoration: "flat", width: 1, height: 1) {
            state "val", label: "-", defaultState: true
        }
        valueTile("space2", "device.space", decoration: "flat", width: 4, height: 1) {
            state "val", label: "월간 전력 사용량(kWh)", defaultState: true
        }
        valueTile("year", "device.year", width: 2, height: 1) {
            state "val", label: "${currentValue}년", defaultState: true
        }
        valueTile("jan", "device.jan", width: 2, height: 1) {
            state "val", label: "1월\n${currentValue}", defaultState: true
        }
        valueTile("feb", "device.feb", width: 2, height: 1) {
            state "val", label: "2월\n${currentValue}", defaultState: true
        }
        valueTile("mar", "device.mar", width: 2, height: 1) {
            state "val", label: "3월\n${currentValue}", defaultState: true
        }
        valueTile("apr", "device.apr", width: 2, height: 1) {
            state "val", label: "4월\n${currentValue}", defaultState: true
        }
        valueTile("may", "device.may", width: 2, height: 1) {
            state "val", label: "5월\n${currentValue}", defaultState: true
        }
        valueTile("jun", "device.jun", width: 2, height: 1) {
            state "val", label: "6월\n${currentValue}", defaultState: true
        }
        valueTile("jul", "device.jun", width: 2, height: 1) {
            state "val", label: "7월\n${currentValue}", defaultState: true
        }
        valueTile("aug", "device.aug", width: 2, height: 1) {
            state "val", label: "8월\n${currentValue}", defaultState: true
        }
        valueTile("sep", "device.sep", width: 2, height: 1) {
            state "val", label: "9월\n${currentValue}", defaultState: true
        }
        valueTile("oct", "device.oct", width: 2, height: 1) {
            state "val", label: "10월\n${currentValue}", defaultState: true
        }
        valueTile("nov", "device.nov", width: 2, height: 1) {
            state "val", label: "11월\n${currentValue}", defaultState: true
        }
        valueTile("dec", "device.dec", width: 2, height: 1) {
            state "val", label: "12월\n${currentValue}", defaultState: true
        }

        main "switch"
        details(["switch",
                 "voltage", "current",
                 "accumulate", "refresh",
                 /*"space1", "space2", "space1",
                 "prev", "year", "next",
                 "jan", "feb", "mar",
                 "apr", "may", "jun",
                 "jul", "aug", "sep",
                 "oct", "nov", "dec"*/])
    }

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
    if (value) {
        voltage = zigbee.convertHexToInt(value)
    }
    voltage = voltage / 1000
    return voltage
}

// 현재 전류
private getCurrent(value) {
    def current = 0
    if (value) {
        current = zigbee.convertHexToInt(value)
    }
    current = current / 1000
    return current
}

// 누적전력
private getAccumlate(currentSummationDelivered, eventStack) {
    def calCrrentSummationDelivered = currentSummationDelivered ?: 0
    calCrrentSummationDelivered = calCrrentSummationDelivered instanceof Number ? calCrrentSummationDelivered : zigbee.convertHexToInt(calCrrentSummationDelivered)
    def calMeteringMultiplier = getSafeNumberValueFromState('meteringMultiplier')
    def calMeteringDivisor = getSafeNumberValueFromState('meteringDivisor')
    if (calMeteringDivisor == 0) {
        calMeteringDivisor = 1
    }
    def accumulateWh = calCrrentSummationDelivered * calMeteringMultiplier / calMeteringDivisor * 1000
    log.debug "accumulateWh: ${accumulateWh}"

    if (state.year == null || state.month == null) {
        log.debug "Do NOT have year & month info"
        clearMonths()
        state.year = Calendar.getInstance().get(Calendar.YEAR)
        state.month = Calendar.getInstance().get(Calendar.MONTH)
        state.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        state.months = [(state.month): accumulateWh]

    } else {
        log.debug "Recorded date: ${state.year}-${state.month}-${state.day}"
        if (Calendar.getInstance().get(Calendar.YEAR) - state.year == 1) {
            log.debug "Year is changed: ${state.year}-${state.month}-${state.day}"
            clearMonths()
            state.year = Calendar.getInstance().get(Calendar.YEAR)
            state.month = Calendar.getInstance().get(Calendar.MONTH)
            state.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            state.months = [(state.month): accumulateWh]

        } else {
            if (Calendar.getInstance().get(Calendar.MONTH) - state.month == 1) {
                log.debug "Year is same but month is changed: ${state.year}-${state.month}-${state.day}"
                state.month = Calendar.getInstance().get(Calendar.MONTH)
                state.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                state.months = [(state.month): accumulateWh]
            } else {
                log.debug "Year and month are same: ${state.year}-${state.month}-${state.day}"
                if (state.day < Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                    log.debug "It is the earliest day of this month"
                    state.months = [(state.month): accumulateWh]
                }
            }
        }
    }
    state.years = [(state.year): state.months]
    log.debug "Recorded yearly info: ${state.years}"
    log.debug "Recorded monthly info: ${state.months}"

    def pattern = "##,###.###"
    def df = new java.text.DecimalFormat(pattern)
    def formatted = df.format(accumulateWh)
    return formatted
}

private clearMonths() {
    state.months = [:]
    for (int i = 0; i < 12; i++) {
        state.months += [(i): 0]
    }
}

private setCalendar(year, accumulateWh, eventStack) {
    log.debug "setCalendar: $year, $accumulateWh, $currentYear, $currentMonth, $years"
    def months = state.years[(year)]
    def currentYear = state.year
    def currentMonth = state.month
    log.debug "TK TEST: ${months}"
    log.debug "TK TEST: ${months[(10)]}"
    def jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec
    def pattern = "##,###.#"
    def df = new java.text.DecimalFormat(pattern)

    if (year == currentYear) {
        log.debug "this year"
        for (int i = 0; i < currentMonth + 1; i++) {
            def usage
            if (i == currentMonth) {
                log.debug "this month"
                usage = accumulateWh - months[(currentMonth)]
            } else {
                if (months[i] != null && months[(i + 1)] != null) {
                    usage = months[(i + 1)] - months[(i)]
                } else {
                    usage = 0
                }
            }
            if (i == 0) {
                if (usage > 0) {
                    jan = df.format(usage)
                } else {
                    jan = "-"
                }
            } else if (i == 1) {
                if (usage > 0) {
                    feb = df.format(usage)
                } else {
                    feb = "-"
                }
            } else if (i == 2) {
                if (usage > 0) {
                    mar = df.format(usage)
                } else {
                    mar = "-"
                }
            } else if (i == 3) {
                if (usage > 0) {
                    apr = df.format(usage)
                } else {
                    apr = "-"
                }
            } else if (i == 4) {
                if (usage > 0) {
                    may = df.format(usage)
                } else {
                    may = "-"
                }
            } else if (i == 5) {
                if (usage > 0) {
                    jun = df.format(usage)
                } else {
                    jun = "-"
                }
            } else if (i == 6) {
                if (usage > 0) {
                    jul = df.format(usage)
                } else {
                    jul = "-"
                }
            } else if (i == 7) {
                if (usage > 0) {
                    aug = df.format(usage)
                } else {
                    aug = "-"
                }
            } else if (i == 8) {
                if (usage > 0) {
                    sep = df.format(usage)
                } else {
                    sep = "-"
                }
            } else if (i == 9) {
                if (usage > 0) {
                    oct = df.format(usage)
                } else {
                    oct = "-"
                }
            } else if (i == 10) {
                if (usage > 0) {
                    nov = df.format(usage)
                } else {
                    nov = "-"
                }
            } else if (i == 11) {
                if (usage > 0) {
                    dec = df.format(usage)
                } else {
                    dec = "-"
                }
            }
        }
        for (int i = currentMonth + 1; i < 12; i++) {
            if (i == 0) {
                jan = "-"
            } else if (i == 1) {
                feb = "-"
            } else if (i == 2) {
                mar = "-"
            } else if (i == 3) {
                apr = "-"
            } else if (i == 4) {
                may = "-"
            } else if (i == 5) {
                jun = "-"
            } else if (i == 6) {
                jul = "-"
            } else if (i == 7) {
                aug = "-"
            } else if (i == 8) {
                sep = "-"
            } else if (i == 9) {
                oct = "-"
            } else if (i == 10) {
                nov = "-"
            } else if (i == 11) {
                dec = "-"
            }
        }
    } else if (year < currentYear) {
        log.debug "last year"
        for (int i = 0; i < 12; i++) {
            def usage
            if (i == 11) {
                usage = years[(year + 1)][0] - months[(i)]
            } else {
                if (months[(i)] != null && months[(i + 1)] != null) {
                    usage = months[(i + 1)] - months[(i)]
                } else {
                    usage = 0
                }
            }
            if (i == 0) {
                if (usage > 0) {
                    jan = df.format(usage)
                } else {
                    jan = "-"
                }
            } else if (i == 1) {
                if (usage > 0) {
                    feb = df.format(usage)
                } else {
                    feb = "-"
                }
            } else if (i == 2) {
                if (usage > 0) {
                    mar = df.format(usage)
                } else {
                    mar = "-"
                }
            } else if (i == 3) {
                if (usage > 0) {
                    apr = df.format(usage)
                } else {
                    apr = "-"
                }
            } else if (i == 4) {
                if (usage > 0) {
                    may = df.format(usage)
                } else {
                    may = "-"
                }
            } else if (i == 5) {
                if (usage > 0) {
                    jun = df.format(usage)
                } else {
                    jun = "-"
                }
            } else if (i == 6) {
                if (usage > 0) {
                    jul = df.format(usage)
                } else {
                    jul = "-"
                }
            } else if (i == 7) {
                if (usage > 0) {
                    aug = df.format(usage)
                } else {
                    aug = "-"
                }
            } else if (i == 8) {
                if (usage > 0) {
                    sep = df.format(usage)
                } else {
                    sep = "-"
                }
            } else if (i == 9) {
                if (usage > 0) {
                    oct = df.format(usage)
                } else {
                    oct = "-"
                }
            } else if (i == 10) {
                if (usage > 0) {
                    nov = df.format(usage)
                } else {
                    nov = "-"
                }
            } else if (i == 11) {
                if (usage > 0) {
                    dec = df.format(usage)
                } else {
                    dec = "-"
                }
            }
        }
    }

    eventStack.push(createEvent(name: "year", value: String.valueOf(year)))
    eventStack.push(createEvent(name: "jan", value: jan))
    eventStack.push(createEvent(name: "feb", value: feb))
    eventStack.push(createEvent(name: "mar", value: mar))
    eventStack.push(createEvent(name: "apr", value: apr))
    eventStack.push(createEvent(name: "may", value: may))
    eventStack.push(createEvent(name: "jun", value: jun))
    eventStack.push(createEvent(name: "jul", value: jul))
    eventStack.push(createEvent(name: "aug", value: aug))
    eventStack.push(createEvent(name: "sep", value: sep))
    eventStack.push(createEvent(name: "oct", value: oct))
    eventStack.push(createEvent(name: "nov", value: nov))
    eventStack.push(createEvent(name: "dec", value: dec))
    if (currentYear == year) {
        eventStack.push(createEvent(name: "next", value: "disabled"))
    } else {
        eventStack.push(createEvent(name: "next", value: "default"))
    }
    if (years[(year - 1)] == null) {
        eventStack.push(createEvent(name: "prev", value: "disabled"))
    } else {
        eventStack.push(createEvent(name: "prev", value: "default"))
    }
    log.debug "year: ${year}"
    log.debug "jan: ${jan}"
    log.debug "feb: ${feb}"
    log.debug "mar: ${mar}"
    log.debug "apr: ${apr}"
    log.debug "may: ${may}"
    log.debug "jun: ${jun}"
    log.debug "jul: ${jul}"
    log.debug "aug: ${aug}"
    log.debug "sep: ${sep}"
    log.debug "oct: ${oct}"
    log.debug "nov: ${nov}"
    log.debug "dec: ${dec}"

    log.debug "eventStack: ${eventStack}"
}

// 순시 전력
private getInstantDemend(instantDemend) {
    def calInstantDemend = instantDemend ?: 0
    calInstantDemend = calInstantDemend instanceof Number ? calInstantDemend : zigbee.convertHexToInt(calInstantDemend)
    def calMeteringMultiplier = getSafeNumberValueFromState('meteringMultiplier')
    def calMeteringDivisor = getSafeNumberValueFromState('meteringDivisor')
    if (calMeteringDivisor == 0) {
        calMeteringDivisor = 1
    }
    def momentaryW = calInstantDemend * calMeteringMultiplier / calMeteringDivisor * 1000

    def pattern = "##,###.###"
    def df = new java.text.DecimalFormat(pattern)
    def formatted = df.format(momentaryW)

    return formatted
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
    if (parseMap != null) {
        clusterId = parseMap.cluster ? parseMap.cluster : parseMap.clusterId
        log.info "LOG-MH: clusterId--1----------- $clusterId"
        clusterId = (clusterId != null) ? clusterId.toUpperCase() : null
        log.info "LOG-MH: clusterId--2----------- $clusterId"
        attrId = parseMap.attrId != null ? parseMap.attrId.toUpperCase() : null
        log.info "LOG-MH: attrId-------------- $attrId"
        log.info "LOG-MH:  parseMap.data-------------- $parseMap.data"
        if (parseMap.data != null) {
            commandData = String.valueOf(parseMap.data[0])
            log.info "LOG-MH: commandData-------------- $commandData"
        }
    }

    def eventStack = []
    if (parseMap != null) {
        def forceReturn = false
        if (clusterId == "0702") {
            def attrProcessor = { theAttrId, value ->
                log.debug "AttributeId: ${theAttrId}"
                if (theAttrId == "0301") {
                    state.meteringMultiplier = value
                    forceReturn = true
                } else if (theAttrId == "0302") {
                    state.meteringDivisor = value
                    forceReturn = true
                } else if (theAttrId == "0000") {
                    def accumulateWh = getAccumlate(value, eventStack)
                    eventStack.push(createEvent(name: "accumulate", value: accumulateWh))
                } else if (theAttrId == "0400") {
                    def instantDemend = getInstantDemend(value)
                    eventStack.push(createEvent(name: "power", value: instantDemend))
                } else if (theAttrId == "0901") {
                    def current = getCurrent(value)
                    eventStack.push(createEvent(name: "currentCurrent", value: current))
                } else if (theAttrId == "0902") {
                    def voltage = getVoltage(value)
                    eventStack.push(createEvent(name: "voltage", value: voltage))
                } else if (theAttrId == "0905") {
                    log.debug "${clusterId}, ${theAttrId}, ${value}, description is ${description}"
                    def powerCutOffEnabled = value == "01" ? "enabled" : "disabled"
                    eventStack.push(createEvent(name: "powerCutOff", value: powerCutOffEnabled))
                } else {
                    log.warn "Unhandle cluster: ${clusterId}, ${theAttrId}, ${value}, description is ${description}"
                }

            };

            def attrs = parseMap.additionalAttrs // 이젝스 확장
            if (attrs == null) {   // 개별 리포팅: Refresh 호출시 개별 attribute는 이 속성으로 읽는다
                attrProcessor(attrId, parseMap.value)
            } else { // 자동 리포팅: 디바이스에서 리포팅하는 정보는 attrs 배열 형태이다.
                attrs += parseMap
                attrs.each { attr ->
                    attrProcessor(attr.attrId, attr.value)
                }
            }
        }
        // log.debug "eventStack: ${eventStack}, ${clusterId}, ${attrId}"
        if (!eventStack.isEmpty()) {
            return eventStack
        }
        if (forceReturn) {
            return
        }
    }
}


def ping() {
    device.endpointId = 1
    return refresh()
}

def prev() {
    log.debug "prev() is called"
}

def next() {
    log.debug "next() is called"
}

def refresh() {
    def endpointId = 1
    def delay = 50
    def cmds = []

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

    log.info "refresh operation requested"
    return cmds
}
